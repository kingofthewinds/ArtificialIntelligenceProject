package sample;

/**
 * Created by lucas on 13/04/17.
 */
public class Network {

    Matrix weightsHiddenLayer;
    Matrix weightsOutputLayer;

    public Network(double[][] weightsHiddenLayer, double[][] weightsOutputLayer) {
        this.weightsHiddenLayer = new Matrix(weightsHiddenLayer);
        this.weightsOutputLayer = new Matrix(weightsOutputLayer);
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

    public Matrix getWeightsHiddenLayer() {
        return weightsHiddenLayer;
    }

    public Matrix getWeightsOutputLayer() {
        return weightsOutputLayer;
    }

    public void setWeightsHiddenLayer(Matrix weightsHiddenLayer) {
        this.weightsHiddenLayer = weightsHiddenLayer;
    }

    public void setWeightsOutputLayer(Matrix weightsOutputLayer) {
        this.weightsOutputLayer = weightsOutputLayer;
    }
}
