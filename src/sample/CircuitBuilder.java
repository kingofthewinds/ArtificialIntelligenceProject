package sample;

import java.util.Stack;

/**
 * Created by kingwinds on 13/04/2017.
 */
public class CircuitBuilder {
    int sizeX;
    int sizeY;
    public CircuitBuilder(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        buildCircuit();
    }

    private void buildCircuit()
    {
        int circuit[][][] = new int[sizeX][sizeY][5];
        for (int i = 0; i < sizeX; i++)
        {
            for (int j = 0; i < sizeY; i++)
            {
                for (int p = 0; i < 5; i++)
                {
                    circuit[i][j][p] = 0;
                }
            }
        }
        int row = 0;
        int col = 0;
        Stack<Integer> history = new Stack<>();
    }
}
