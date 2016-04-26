import org.ejml.simple.SimpleMatrix;

/**
 * Created by Diarmuid on 13/04/2016.
 */
public class Example {
    public MyMatrix input;
    public MyMatrix output;
    public Example(MyMatrix input, MyMatrix output) {
        this.input = input;
        this.output = output;
    }
    public Example(double[] input, double[] output) {
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
