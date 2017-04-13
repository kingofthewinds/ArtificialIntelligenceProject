package sample;

/**
 * Created by lucas on 13/04/17.
 */
public class Network {

    Matrix weightsHiddenLayer;
    Matrix weightsOutputLayer;

    public Network(double[][] output, double[][] weightsHiddenLayer, double[][] weightsOutputLayer) {
        this.weightsHiddenLayer = new Matrix(weightsHiddenLayer);
        this.weightsOutputLayer = new Matrix(weightsOutputLayer);
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
