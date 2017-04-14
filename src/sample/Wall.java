package sample;

import javafx.scene.shape.Line;

/**
 * Created by kingwinds on 14/04/2017.
 */
public class Wall extends Line {
    private int row;
    private int col;


    public Wall(double startX, double startY, double endX, double endY, int col, int row){
        super(startX,startY,endX,endY);
        this.row = row;
        this.col = col;

    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}
