package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {

    private int tickDuration = 10;
    private int duration = 10;
    public int circuitLength;

    @FXML
    Button newIterationButton;

    @FXML
    ProgressBar progressBar;

    @FXML
    Pane circuitPanel;

    @FXML
    Spinner spinner;

    @FXML
    CheckBox showBars;

    @FXML
    CheckBox autorun;

    @FXML
    Label iteration;

    @FXML
    Label alive;

    @FXML
    Label dead;

    @FXML
    Label car1;

    @FXML
    Label car2;

    @FXML
    Label car3;

    @FXML
    Label car4;

    @FXML
    Label car5;

    @FXML
    Label car6;

    @FXML
    Label car7;

    @FXML
    Label car8;

    @FXML
    Label car9;

    @FXML
    Label car10;

    @FXML
    Label cartotal;

    @FXML
    Label bestScore;

    public int numberOfCars = 500;
    int sizeCircuit = 14;
    double scale = 40;



    private Circuit circuit;
    private GeneticAlgorithm genetic;
    private int ite = 1;
    public List<Car> cars;
    public List<Car> newcars;
    private int theBestScore = 0;
    private int bestPilot = 0;
    private int numberOfCarAlive = 0;
    ArrayList<Wall> walls = new ArrayList<>();


    @FXML
    private void initialize()
    {
        spinner.getValueFactory().setValue(10);
        this.genetic = new GeneticAlgorithm(numberOfCars,scale,this);
        this.circuit = new Circuit(scale);
        drawCircuit();
        this.cars = genetic.generateNewPopulation(numberOfCars, scale, this);
        beginIteration();


    }




    private void drawCircuit() {
        Maze m = new Maze(sizeCircuit);
        ArrayList<int[]> contour = m.getCircuit();
        circuitLength = contour.size();
        for (int n = 1 ; n < contour.size()-1; n ++) {
            int prevX = contour.get(n-1)[0];
            int prevY = contour.get(n-1)[1];
            int x = contour.get(n)[0];
            int y = contour.get(n)[1];
            int nextX = contour.get(n+1)[0];
            int nextY = contour.get(n+1)[1];

            circuit.setBlock(x, y);

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
        newIterationButton.setDisable(true);

        for (Car car : this.cars) {
            circuitPanel.getChildren().add(car);
            if (showBars.isSelected()) {
                for (Sensor sensor : car.getSensors()) {
                    circuitPanel.getChildren().add(sensor);
                }
            }
        }

        new Thread(new Runnable() {

            int time = 0; // in milli seconds
            @Override
            public void run() {
                while(true){
                    try {

                        Platform.runLater(() -> {
                            cars.get(0).setFill(Color.BLACK);
                            tick();
                            int death = 0;
                            int living = 0;
                            int score = 0;
                            numberOfCarAlive = 0;
                            for(Car c : cars)
                            {
                                if (c.isCrashed()){
                                    death++;
                                }
                                else {
                                    living++;
                                    numberOfCarAlive ++;
                                }
                                score += c.score;
                                if (score > theBestScore) {
                                    theBestScore = score;
                                }
                            }
                            alive.setText("Alive = " + String.valueOf(living));
                            dead.setText("Dead = " + String.valueOf(death));

                            Collections.sort(cars, new Comparator<Car>() {
                                @Override
                                public int compare(Car o1, Car o2) {
                                    return o2.score - o1.score;
                                }
                            });
                            iteration.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
                            iteration.setText("Iteration : " + ite);
                            car1.setText("Pilot n°1 : " + cars.get(0).score + "\navg speed : " + (int)(100*cars.get(0).getAverageSpeed(time)) + "\n");
                            car2.setText("Pilot n°2 : " + cars.get(1).score);
                            car3.setText("Pilot n°3 : " + cars.get(2).score);
                            car4.setText("Pilot n°4 : " + cars.get(3).score);
                            car5.setText("Pilot n°5 : " + cars.get(4).score);
                            car6.setText("Pilot n°6 : " + cars.get(5).score);
                            car7.setText("Pilot n°7 : " + cars.get(6).score);
                            car8.setText("Pilot n°8 : " + cars.get(7).score);
                            car9.setText("Pilot n°9 : " + cars.get(8).score);
                            car10.setText("Pilot n°10 : " + cars.get(9).score);
                            cartotal.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
                            cartotal.setText("Total score : \n" + score);
                            bestScore.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
                            bestScore.setText("Best score : \n" + theBestScore + "\nBest Pilot : " + bestPilot);

                            if (cars.get(0).score > bestPilot) {
                                bestPilot = cars.get(0).score;
                            }
                            cars.get(0).setFill(Color.GREEN);

                        });
                        time += tickDuration;
                        progressBar.setProgress((double)time/(duration*1000));
                        Thread.sleep(tickDuration);

                        if (time >= duration*1000 || numberOfCarAlive == 0) {
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
        this.newcars = genetic.breedPopulation(calculateBestCars(),ite);
        newIterationButton.setDisable(false);
        if (autorun.isSelected()) {
            Platform.runLater(() -> {
                mouseClickedNewIteration();
            });
        }

    }

    private ArrayList<Car> calculateBestCars() {
        Collections.sort(this.cars, new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                return o2.score - o1.score;
            }
        });

        int numberCars = (int)numberOfCars/20 + ite;
        if (numberCars > this.cars.size()){
            numberCars = this.cars.size()-1;
        }
        return new ArrayList(this.cars.subList(0,numberCars));
    }

    private void tick() {
        for (int i=0; i<this.cars.size(); i++) {

            cars.get(i).tick();
        }
    }

    @FXML
    private void mouseClickedNewIteration()
    {
        circuitPanel.getChildren().clear();
        walls.clear();
        this.cars = this.newcars;
        duration = (int)spinner.getValue();
        ite++;
        drawCircuit();
        beginIteration();

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
