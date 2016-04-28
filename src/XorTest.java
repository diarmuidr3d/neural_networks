
/**
 * Created by Diarmuid on 15/04/2016.
 * Train an MLP with 2 inputs, two hidden units and one output on the
 following examples (XOR function):
 ((0, 0), 0)
 ((0, 1), 1)
 ((1, 0), 1)
 ((1, 1), 0)
 */
public class XorTest {

    private static double[] sample0In = {0, 0};
    private static double[] sample0Out = {0};

    private static double[] sample1In = {0, 1};
    private static double[] sample1Out = {1};

    private static double[] sample2In = {1, 0};
    private static double[] sample2Out = {1};

    private static double[] sample3In = {1, 1};
    private static double[] sample3Out = {0};

    public static void main (String[] args) {
        Example[] examples = new Example[4];
        examples[0] = new Example(sample0In, sample0Out);
        examples[1] = new Example(sample1In, sample1Out);
        examples[2] = new Example(sample2In, sample2Out);
        examples[3] = new Example(sample3In, sample3Out);

        System.out.println("Sin Test: Starting Learning");
        MLP mlp = new MLP(2, 3, 1);
        System.out.println("Error for 0.7: " + mlp.learn(60000, examples, 0.7, MyMatrix.LOGISTIC_SQAUSH));

        System.out.println("Result from example 0: " + mlp.forward(examples[0].input, MyMatrix.LOGISTIC_SQAUSH).get(0) + " should be " + examples[0].output.get(0));
        System.out.println("Result from example 1: " + mlp.forward(examples[1].input, MyMatrix.LOGISTIC_SQAUSH).get(0) + " should be " + examples[1].output.get(0));
        System.out.println("Result from example 2: " + mlp.forward(examples[2].input, MyMatrix.LOGISTIC_SQAUSH).get(0) + " should be " + examples[2].output.get(0));
        System.out.println("Result from example 3: " + mlp.forward(examples[3].input, MyMatrix.LOGISTIC_SQAUSH).get(0) + " should be " + examples[3].output.get(0));
    }
}
