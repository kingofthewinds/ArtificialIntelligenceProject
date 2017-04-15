package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 13/04/17.
 */
public class Network {

    private final Matrix weightsHiddenLayer;
    private final Matrix weightsOutputLayer;


    public Network(Network copy)
    {
        this.weightsHiddenLayer = new Matrix(copy.weightsHiddenLayer);
        this.weightsOutputLayer = new Matrix(copy.weightsOutputLayer);
    }

    public Network(Matrix weightsHiddenLayer, Matrix weightsOutputLayer) {
        this.weightsHiddenLayer = weightsHiddenLayer;
        this.weightsOutputLayer = weightsOutputLayer;

    }



    public Network(int nbrInput, int nbrOutput, int nbrHiddenNeurons) {
        this.weightsHiddenLayer = Matrix.random(nbrHiddenNeurons, nbrInput);
        this.weightsOutputLayer = Matrix.random(nbrOutput, nbrHiddenNeurons);

    }

    public Matrix evaluate(Matrix input) {
        Matrix outputHiddenLayer = this.weightsHiddenLayer.times(input).sigmoid();
        Matrix output = this.weightsOutputLayer.times(outputHiddenLayer).sigmoid();
        return output;
    }

    public List<Matrix> getWeights() {
        ArrayList<Matrix> weights =  new ArrayList<>();
        weights.add(this.weightsHiddenLayer);
        weights.add(this.weightsOutputLayer);
        return weights;
    }

    public Matrix getWeightsHiddenLayer() {
        return weightsHiddenLayer;
    }

    public Matrix getWeightsOutputLayer() {
        return weightsOutputLayer;
    }
}
