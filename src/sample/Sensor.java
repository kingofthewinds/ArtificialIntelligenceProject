package sample;

import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Created by kingwinds on 14/04/2017.
 */
public class Sensor extends Line {
    private double distance ;


    public Sensor(double startX, double startY, double endX, double endY){
        super(startX,startY,endX,endY);
    }

    public static double[] lineIntersect(Line wall, Line sensor) {

        double x1 = wall.getStartX();
        double y1 = wall.getStartY();
        double x2 = wall.getEndX();
        double y2 = wall.getEndY();
        double x3 = sensor.getStartX();
        double y3 = sensor.getStartY();
        double x4 = sensor.getEndX();
        double y4 = sensor.getEndY();

        double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (denom == 0.0) { // Lines are parallel.
            return null;
        }
        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3))/denom;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3))/denom;
        if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
            // Get the intersection point.
            return new double[]{  (x1 + ua*(x2 - x1)),  (y1 + ua*(y2 - y1))  };
        }
        return null;
    }

    public double sense(ArrayList<Wall> walls)
    {
        double dist = Double.MAX_VALUE;
        double d;
        double[] intersectPoint;
        for (Wall w : walls)
        {
            if ((intersectPoint = lineIntersect(w,this)) != null)
            {
                d = Math.sqrt( Math.pow(intersectPoint[0]-this.getStartX(),2) + Math.pow(intersectPoint[1]-this.getStartY(),2));
                if (d < dist)
                {
                    dist = d;
                }
            }
        }
        this.distance = dist;
        return dist;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
