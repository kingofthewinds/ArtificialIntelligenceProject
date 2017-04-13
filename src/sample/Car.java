package sample;

/**
 * Created by lucas on 13/04/17.
 */
public class Car {

    private double x;
    private double y;
    private double angle;

    public void Car(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    private void move(double distance) {
        double dx = Math.cos(distance * this.angle);
        double dy = Math.sin(distance * this.angle);

        this.x += dx;
        this.y += dy;
    }

    private void turn(float angle) {
        this.angle += angle;
    }

}
