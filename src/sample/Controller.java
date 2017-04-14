package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    Pane circuit;

    private List<Car> cars;
    double scale = 90;
    ArrayList<Wall> walls = new ArrayList<>();


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
            Wall lineN = new Wall(x*scale, y*scale, (x+1)*scale, y*scale, x,y);
            //Lower Line
            Wall lineS = new Wall(x*scale, (y+1)*scale, (x+1)*scale, (y+1)*scale,x,y);
            //Right Line
            Wall lineE = new Wall((x+1)*scale, y*scale, (x+1)*scale, (y+1)*scale,x,y);
            //LeftLine
            Wall lineW = new Wall(x*scale, y*scale, x*scale, (y+1)*scale,x,y);


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
        else {
            circuit.getChildren().add(new Wall(2 * scale, scale, 2 * scale, 2 * scale, 1, 1));
        }
        for (Node w : circuit.getChildren())
        {
            walls.add((Wall) w);
        }
    }

    private void beginIteration() {
        this.cars = new ArrayList<>();
        for (int i=0; i<15; i++) {
            Car car = new Car(1*scale+scale/2,1*scale+scale/2, 0, scale);
            this.cars.add(car);
            circuit.getChildren().add(car);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Platform.runLater(() -> {clearCars();});

                        tick();
                        //car.move(10);
                        Platform.runLater(() -> {showCars();});
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void tick() {
        for (int i=0; i<this.cars.size(); i++) {

            cars.get(i).tick();
        }
    }

    public void showCars() {
        for (int i=0; i<this.cars.size(); i++) {
            circuit.getChildren().add(cars.get(i));
        }
    }


    public void clearCars() {
        for (int i=0; i<this.cars.size(); i++) {
            circuit.getChildren().remove(cars.get(i));
        }
    }

    @FXML
    private void mouseClicked()
    {
        System.out.println("hello");
    }

    public ArrayList<Wall> getWallsInPerimeters(int x, int y)
    {
        ArrayList<Wall> interestingWalls = new ArrayList<>();
        for (Wall w : walls)
        {
            double wallX = w.getCol();
            double wallY = w.getRow();
            if (Math.abs(wallX-x) <= 2 && Math.abs(wallY-y) <= 2)
            {
                interestingWalls.add(w);
            }
        }

        return interestingWalls;
    }



}
