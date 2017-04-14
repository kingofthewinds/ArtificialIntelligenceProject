package sample;

import javafx.scene.shape.Circle;

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

    private Controller controller;
    private double angle;
    private Network brain;
    private boolean crashed = false;
    private static final double maxSpeed = 10;
    private static final double maxTurn = Math.PI/4;

            ;

    public Car(double x, double y, double angle,double scale, Controller controller) {
        super(x,y,scale/4);
        this.angle = angle;
        this.controller = controller;
        this.brain = new Network(7, 2, 5);
    }

    public void tick() {
        if (!crashed) {
            updateSensors();
            Matrix input = Matrix.random(7, 1);; //get sensor infos
            Matrix output= this.brain.evaluate(input); // Output is a 1x2 matrix
            double distToTravel = output.get(0);
            double angle = (output.get(1) * 2)-1; // rebase angle between -1 and 1
            this.move(distToTravel * this.maxSpeed);
            this.turn(angle * this.maxTurn);
        }
        this.crashed = this.checkCrash();
    }

    private void updateSensors() {
    }

    private boolean checkCrash() {
        ArrayList<Wall> walls = controller.getWallsInPerimeters(getCenterX(),getCenterY());
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).collide(this)) {
                return true;
            }
        }
        return false;
    }

    public void move(double distance) {
        double dx = distance * Math.cos(this.angle);
        double dy = distance * Math.sin(this.angle);


        this.setCenterX(this.getCenterX()+dx);
        this.setCenterY(this.getCenterY()+dy);
    }

    public void turn(double angle) {
        this.angle += angle;
        System.out.println(this.angle);
    }

}
