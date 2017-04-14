package sample;

/**
 * Created by lucas on 13/04/17.
 */
public class Car {

    private double x;
    private double y;
    private double angle;
    private Network brain;
    private static final double maxSpeed = 10;
    private static final double maxTurn = Math.PI/3;

    public void Car(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.brain = new Network(7, 2, 5);
    }

    public void tick() {
        Matrix input = Matrix.random(1, 2);; //get sensor infos
        Matrix output= this.brain.evaluate(input); // Output is a 1x2 matrix
        double distToTravel = output.get(0);
        double angle = output.get(1);
        this.move(distToTravel * this.maxSpeed);
        this.turn(angle * this.maxTurn);


    }

    private void move(double distance) {
        double dx = Math.cos(distance * this.angle);
        double dy = Math.sin(distance * this.angle);

        this.x += dx;
        this.y += dy;
    }

    private void turn(double angle) {
        this.angle += angle;
    }

}
