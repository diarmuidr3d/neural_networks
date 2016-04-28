/**
 * Created by Diarmuid Ryan - 11363776.
 */
class MLP {
    private MyMatrix lowerLayerWeights;
    private MyMatrix upperLayerWeights;
    private MyMatrix lowerLayerWeightChanges;
    private MyMatrix upperLayerWeightChanges;
    private MyMatrix lastInput;
    private MyMatrix hiddenNeuronValues;
    private MyMatrix outputs;

    MLP(int numberOfInputs, int numberOfHiddenUnits, int numberOfOutputs) {
        lowerLayerWeights = smallRandMatrix(numberOfInputs, numberOfHiddenUnits);
        upperLayerWeights = smallRandMatrix(numberOfHiddenUnits, numberOfOutputs);
        lowerLayerWeightChanges = new MyMatrix(lowerLayerWeights.numRows(), lowerLayerWeights.numCols());
        lowerLayerWeightChanges.zero();
        upperLayerWeightChanges = new MyMatrix(upperLayerWeights.numRows(), upperLayerWeights.numCols());
        upperLayerWeightChanges.zero();
    }

    /**
     * Forward pass. Input vector I is processed to produce an output, which is stored in outputs.
     * @param input the input vector which will be processed to produce an output
     */
    MyMatrix forward(MyMatrix input, int squashFunction) {
        hiddenNeuronValues = input.mult(lowerLayerWeights).squash(squashFunction);
        outputs = hiddenNeuronValues.mult(upperLayerWeights).squash(squashFunction);
        lastInput = input;
        return outputs;
    }

    /**
     * Backwards pass. Target target is compared with output O, deltas are computed
     for the upper layer, and are multiplied by the inputs to the layer (the
     values in H) to produce the weight updates which are stored in dW2
     (added to it, as you may want to store these for many examples). Then
     deltas are produced for the lower layer, and the same process is
     repeated here, producing weight updates to be added to dW1.
     Returns the error on the example.
     * @param target Target
     */
    private double backward(MyMatrix target, int squashFunction) {
        MyMatrix outputError = target.minus(outputs);
        double totalError = outputError.abs().elementSum();
        MyMatrix upperDelta = outputError.multiplyIndividual(outputs.derivativeSquash(squashFunction));
        upperLayerWeightChanges = upperLayerWeightChanges.plus(hiddenNeuronValues.transpose().mult(upperDelta));
        MyMatrix hiddenError = upperDelta.mult(upperLayerWeights.transpose());
        MyMatrix lowerDelta = hiddenError.multiplyIndividual(hiddenNeuronValues.derivativeSquash(squashFunction));
        lowerLayerWeightChanges = lowerLayerWeightChanges.plus(lastInput.transpose().mult(lowerDelta));
        return totalError;
    }

    double learn(int maxEpochs, Example[] example, double learningRate, int squashFunction) {
        double error = 0;
        for (int e=0; e<maxEpochs; e++) {
            error = 0;
            for (Example anExample : example) {
                forward(anExample.input, squashFunction);
                error += backward(anExample.output, squashFunction);
//                if ( maxEpochs % 100 == 0) {
                    updateWeights(learningRate);
//                }
            }
        }
        System.out.println("Learning error after " + maxEpochs + " epochs is " + error);
        return error;
    }

    /**
     * this simply does (component by component, i.e. within for loops):
     W1 += learningRate*dW1;
     W2 += learningRate*dW2;
     dW1 = 0;
     dW2 = 0;
     * @param learningRate the rate at which the MLP learns
     */
    private void updateWeights(double learningRate) {
        lowerLayerWeights = lowerLayerWeights.plus(learningRate, lowerLayerWeightChanges);
        upperLayerWeights = upperLayerWeights.plus(learningRate, upperLayerWeightChanges);
        lowerLayerWeightChanges.zero();
        upperLayerWeightChanges.zero();
    }
    private MyMatrix smallRandMatrix(int rows, int cols) {
        MyMatrix matrix = new MyMatrix(rows, cols);
        for (int i = 0; i < matrix.numCols(); i++) {
            for (int j = 0; j < matrix.numRows(); j++) {
                double randNum = (2 * Math.random() - 1) * 0.05;
                matrix.set(j, i, randNum);
            }
        }
        return matrix;
    }
}
