package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

/**
 * Created by lucas on 13/04/17.
 */
public class Car extends Circle {

    private Sensor forward;
    private Sensor forwardLeft;
    private Sensor forwardRight;
    private Sensor left;
    private Sensor right;
    private ArrayList<Sensor> sensors = new ArrayList<>();

    private double scale;
    private Controller controller;
    private double angle;
    private Network brain;
    private boolean crashed = false;
    private static double maxSpeed;
    private static final double maxTurn = Math.PI/4;

    public ArrayList<int []> visited = new ArrayList<>();
    int score = 0;
    private double totalDistance = 0;


    public Car(double x, double y, double angle,double scale, Controller controller) {
        super(x,y,scale/8);
        this.angle = angle;
        this.controller = controller;
        this.brain = new Network(5, 2, 5);
        this.scale = scale;
        maxSpeed = scale/8;
        createSensors(scale);
    }

    private void createSensors(double scale) {
        forward = new Sensor(getCenterX(),getCenterY(),getCenterX()+3*scale,getCenterY());
        forward.setOpacity(0.1);
        left = new Sensor(getCenterX(),getCenterY(),getCenterX(),getCenterY()-3*scale);
        left.setOpacity(0.1);
        right = new Sensor(getCenterX(),getCenterY(),getCenterX(),getCenterY()+3*scale);
        right.setOpacity(0.1);
        forwardRight = new Sensor(getCenterX(),getCenterY(),getCenterX()+2*scale,getCenterY()+2*scale);
        forwardRight.setOpacity(0.1);
        forwardLeft = new Sensor(getCenterX(),getCenterY(),getCenterX()+2*scale,getCenterY()-2*scale);
        forwardLeft.setOpacity(0.1);
        sensors.add(forward);
        sensors.add(left);
        sensors.add(right);
        sensors.add(forwardLeft);
        sensors.add(forwardRight);
    }

    public void tick() {
        if (!crashed) {
            sense();


            //Matrix input = Matrix.random(5, 1);; //get sensor infos
            Matrix input = new Matrix(new double[][] {
                    {forward.getDistance()},
                    {forwardLeft.getDistance()},
                    {forwardRight.getDistance()},
                    {left.getDistance()},
                    {right.getDistance()}
            }); //get sensor infos
            //input.show();
            Matrix output= this.brain.evaluate(input); // Output is a 1x2 matrix
            double distToTravel = output.get(0);
            double angle = (output.get(1) * 2)-1; // rebase angle between -1 and 1
            this.turn(angle * this.maxTurn);
            this.move(distToTravel * this.maxSpeed);


            updateScore();
        }
        this.crashed = this.checkCrash();
    }



    private void sense() {
        ArrayList<Wall> walls = controller.getWallsInPerimeters(getCenterX(),getCenterY());
        forward.sense(walls);
        forwardLeft.sense(walls);
        forwardRight.sense(walls);
        left.sense(walls);
        right.sense(walls);



    }

    private boolean checkCrash() {
        ArrayList<Wall> walls = controller.getWallsInPerimeters(getCenterX(),getCenterY());
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).collide(this)) {
                this.setFill(Color.RED);
                this.setOpacity(0.1);
                for (Sensor s : this.getSensors())
                {
                    s.setOpacity(0);
                }
                return true;
            }
        }
        return false;
    }

    public void move(double distance) {
        this.totalDistance += distance;
        double dx = distance * Math.cos(this.angle);
        double dy = distance * Math.sin(this.angle);



        this.setCenterX(this.getCenterX()+dx);
        this.setCenterY(this.getCenterY()+dy);

        for (Sensor s : sensors)
        {
            s.setStartX(s.getStartX()+dx);
            s.setStartY(s.getStartY()+dy);
            s.setEndX(s.getEndX()+dx);
            s.setEndY(s.getEndY()+dy);
        }
    }

    public double getAverageSpeed(int time) {
        return this.totalDistance / time;
    }

    public void turn(double angle) {
        this.angle += angle;
        for (Sensor s : sensors)
        {
            Rotate rotate = new Rotate(angle,s.getStartX(),s.getStartY());


            double xs = s.getEndX() - s.getStartX();
            double ys = s.getEndY() - s.getStartY();

            double xPrim = xs*Math.cos(angle) - ys*Math.sin(angle);
            double yPrim = xs*Math.sin(angle) + ys*Math.cos(angle);

            s.setEndX(s.getStartX()+xPrim);
            s.setEndY(s.getStartY()+yPrim);

        }
        //System.out.println(this.angle);
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public Network getNetwork() {
        return brain;
    }

    private void updateScore() {
        if (controller.circuitLength == visited.size())
        {
            visited.clear();
        }
        int x = (int)this.getCenterX()/(int)scale;
        int y = (int)this.getCenterY()/(int)scale;
        for (int [] visitednode : visited)
        {
            if (visitednode[0] == x && visitednode[1] == y)
            {
                return;
            }
        }
        visited.add(new int[]{x,y});
        this.score++;
    }


    public void setBrain(Network brain) {
        this.brain = brain;
    }

    public boolean isCrashed() {
        return crashed;
    }
}
