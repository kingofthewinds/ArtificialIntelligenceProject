package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Controller {

    @FXML
    Pane circuit;

    Car car;
    double scale = 90;


    @FXML
    private void initialize()
    {
        drawCircuit();
        beginIteration();


    }




    private void drawCircuit() {
        Maze m = new Maze(5);
        ArrayList<int[]> contour = m.getCircuit();
        for (int n = 1 ; n < contour.size()-1; n ++) {

            int prevX = contour.get(n-1)[0];
            int prevY = contour.get(n-1)[1];
            int x = contour.get(n)[0];
            int y = contour.get(n)[1];
            int nextX = contour.get(n+1)[0];
            int nextY = contour.get(n+1)[1];
            System.out.println(x + "; " +y );
            //Upper line
            Line lineN = new Wall(x*scale, y*scale, (x+1)*scale, y*scale, x,y);
            //Lower Line
            Line lineS = new Wall(x*scale, (y+1)*scale, (x+1)*scale, (y+1)*scale,x,y);
            //Right Line
            Line lineE = new Wall((x+1)*scale, y*scale, (x+1)*scale, (y+1)*scale,x,y);
            //LeftLine
            Line lineW = new Wall(x*scale, y*scale, x*scale, (y+1)*scale,x,y);


            //Goes down
            if (x == nextX && nextY == y+1)
            {
                lineS = null;
            }
            //Goes up
            else if (x == nextX && nextY == y-1)
            {
                lineN = null;
            }
            //Goes right
            else if (y == nextY && nextX == x+1)
            {
                lineE = null;
            }
            //Goes left
            else
            {
                lineW = null;
            }

            //Comes from down
            if (prevX == x && prevY == y+1)
            {
                lineS = null;
            }
            //Goes up
            else if (prevX == x && prevY == y-1)
            {
                lineN = null;
            }
            //Goes right
            else if (prevY == y && prevX == x+1)
            {
                lineE = null;
            }
            //Goes left
            else
            {
                lineW = null;
            }

            if (lineN != null)
            {
                circuit.getChildren().add(lineN);
            }
            if (lineS != null)
            {
                circuit.getChildren().add(lineS);
            }
            if (lineE != null)
            {
                circuit.getChildren().add(lineE);
            }
            if (lineW != null)
            {
                circuit.getChildren().add(lineW);
            }
        }

        circuit.getChildren().add(new Wall(scale,0,2*scale,0,1,0));
        circuit.getChildren().add(new Wall(scale,0,scale,scale , 1,0));
        circuit.getChildren().add(new Wall(scale,scale,scale,2*scale, 1,1));
        if (contour.get(1)[0] == 2)
        {
            circuit.getChildren().add(new Wall(scale,2*scale,2*scale,2*scale, 1,1));
        }
        else
        {
            circuit.getChildren().add(new Wall(2*scale,scale,2*scale,2*scale,1,1));
        }
    }

    private void beginIteration() {
        this.car = new Car(1*scale+scale/2,1*scale+scale/2, 0, scale);
        circuit.getChildren().add(car);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Platform.runLater(() -> {circuit.getChildren().remove(car);});

                        car.tick();
                        //car.move(10);
                        Platform.runLater(() -> {circuit.getChildren().add(car);});
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @FXML
    private void mouseClicked()
    {
        System.out.println("hello");
    }





}
