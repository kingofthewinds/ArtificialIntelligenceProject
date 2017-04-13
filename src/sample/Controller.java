package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Controller {

    @FXML
    Pane circuit;

    float scale = 20;


    @FXML
    private void initialize()
    {
        Maze m = new Maze(30);
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
            Line lineN = new Line(x*scale, y*scale, (x+1)*scale, y*scale);
            //Lower Line
            Line lineS = new Line(x*scale, (y+1)*scale, (x+1)*scale, (y+1)*scale);
            //Right Line
            Line lineE = new Line((x+1)*scale, y*scale, (x+1)*scale, (y+1)*scale);
            //LeftLine
            Line lineW = new Line(x*scale, y*scale, x*scale, (y+1)*scale);

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

    }

    @FXML
    private void mouseClicked()
    {
        System.out.println("hello");
    }





}
