import java.util.Arrays;

/**
 * Created by Diarmuid Ryan.
 */
public class SinTest {

    public static Example[] generateExamples() {
        Example[] retVal = new Example[50];
        for(int i = 0; i < retVal.length; i++) {
            double[][] randomVector = {{random(), random(), random(), random()}};
            MyMatrix input = new MyMatrix(randomVector);
            double[][] outputVector = {{Math.sin(input.elementSum())}};
            MyMatrix output = new MyMatrix(outputVector);
            retVal[i] = new Example(input, output);
        }
        return retVal;
    }

    private static double random() {
        return 2 * Math.random() - 1;
    }

    public static void main (String[] args) {
        Example[] randomVectors = generateExamples();
        MLP mlp = new MLP(4, 10, 1);
        mlp.learn(200000, Arrays.copyOfRange(randomVectors,0,40), 0.01, MyMatrix.BIPOLAR_SQUASH);

        Example[] testSet = Arrays.copyOfRange(randomVectors,40,randomVectors.length);
        double error = 0;
        for (Example example : testSet) {
            error += mlp.forward(example.input, MyMatrix.BIPOLAR_SQUASH).minus(example.output).abs().get(0);
        }
        System.out.println("Error on test set: " + error);
    }
}
