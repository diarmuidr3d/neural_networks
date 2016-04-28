import org.ejml.simple.SimpleMatrix;

/**
 * Created by Diarmuid Ryan.
 */
class Example {
    MyMatrix input;
    MyMatrix output;
    Example(MyMatrix input, MyMatrix output) {
        this.input = input;
        this.output = output;
    }
    Example(double[] input, double[] output) {
        this.input = matrixFromArray(input);
        this.output = matrixFromArray(output);
    }
    private MyMatrix matrixFromArray(double[] array) {
        MyMatrix matrix = new MyMatrix(1, array.length);
        for (int i = 0; i < array.length; i++) {
            matrix.set(0,i, array[i]);
        }
        return matrix;
    }
}
