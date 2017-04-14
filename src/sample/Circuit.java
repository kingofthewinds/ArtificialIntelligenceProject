package sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lucas on 14/04/17.
 */
public class Circuit {
    private Map<String, Integer> blocks;
    private int counter = 0;
    private double scale;

    public Circuit(double scale) {
        this.blocks = new HashMap<>();
        this.scale = scale;
    }

    public void setBlock(int indexX, int indexY) {
        blocks.put(key(indexX, indexY), counter);
        counter++;
    }

    public int getDistanceTraveled(double x, double y) { // x and y are in real world coordinate
        int indexX = (int)(x / scale);
        int indexY = (int)(y / scale);
        return blocks.get(key(indexX, indexY));
    }

    private String key(int indexX, int indexY) {
        return "" + indexX + indexX;
    }
}
