package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    Pane circuitPanel;
    private Circuit circuit;
    private GeneticAlgorithm genetic;
    public int numberOfCars = 1;

    public List<Car> cars;
    double scale = 90;
    ArrayList<Wall> walls = new ArrayList<>();


    @FXML
    private void initialize()
    {
        this.genetic = new GeneticAlgorithm();
        this.circuit = new Circuit(scale);
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

            circuit.setBlock(x, y);

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
                circuitPanel.getChildren().add(lineN);
            }
            if (lineS != null)
            {
                circuitPanel.getChildren().add(lineS);
            }
            if (lineE != null)
            {
                circuitPanel.getChildren().add(lineE);
            }
            if (lineW != null)
            {
                circuitPanel.getChildren().add(lineW);
            }
        }

        circuitPanel.getChildren().add(new Wall(scale,0,2*scale,0,1,0));
        circuitPanel.getChildren().add(new Wall(scale,0,scale,scale , 1,0));
        circuitPanel.getChildren().add(new Wall(scale,scale,scale,2*scale, 1,1));
        if (contour.get(1)[0] == 2)
        {
            circuitPanel.getChildren().add(new Wall(scale,2*scale,2*scale,2*scale, 1,1));
        }
        else {
            circuitPanel.getChildren().add(new Wall(2 * scale, scale, 2 * scale, 2 * scale, 1, 1));
        }
        for (Node w : circuitPanel.getChildren())
        {
            walls.add((Wall) w);
        }

    }

    private void beginIteration() {

        this.cars = genetic.generateNewPopulation(numberOfCars, scale, this);

        for (Car car : this.cars) {
            circuitPanel.getChildren().add(car);
            for (Sensor sensor : car.getSensors())
            {
                circuitPanel.getChildren().add(sensor);
            }
        }

        new Thread(new Runnable() {

            int time = 0; // in milli seconds
            int tps = 10;
            int duration = 8; // in seconds
            @Override
            public void run() {
                while(true){
                    try {
                        //Platform.runLater(() -> {clearCars();});


                        tick();
                        //car.move(10);
                        //Platform.runLater(() -> {showCars();});
                        int dt = 1000/tps;
                        time += dt;
                        Thread.sleep(dt);

                        if (time == duration*1000) {
                            break;
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopIteration();
            }
        }).start();

    }

    public void stopIteration() {
        genetic.breedPopulation(this.cars);
    }

    private void tick() {
        for (int i=0; i<this.cars.size(); i++) {

            cars.get(i).tick();
        }
    }

    public void showCars() {
        for (int i=0; i<this.cars.size(); i++) {
            cars.get(i).setOpacity(0.1);
            circuitPanel.getChildren().add(cars.get(i));
        }
    }


    public void clearCars() {
        for (int i=0; i<this.cars.size(); i++) {
            circuitPanel.getChildren().remove(cars.get(i));
        }
    }

    @FXML
    private void mouseClicked()
    {
        System.out.println("hello");
    }

    public ArrayList<Wall> getWallsInPerimeters(double xd, double yd)
    {
        int x = (int)(xd / scale);
        int y = (int)(yd / scale);
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
