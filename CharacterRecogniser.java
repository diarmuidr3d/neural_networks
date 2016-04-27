import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Diarmuid Ryan.
 */
public class CharacterRecogniser {

    private static double sizeOfTestSet = 0.2;

    private static MyMatrix charToOutputMatrix(char character) {
        MyMatrix retVal = new MyMatrix(1, 26);
        retVal.zero();
        retVal.set(((int) character) - 65, 1);
        return retVal;
    }

    private static MyMatrix inputArrayToMatrix(String[] inputs) {
//        MyMatrix retVal = new MyMatrix(1, 17);
//        retVal.zero();
//        for (String input : inputs) {
//            retVal.set(Integer.parseInt(input), 1);
//        }
        MyMatrix retVal = new MyMatrix(1, 16);
        retVal.zero();
        for (int i = 0; i < inputs.length; i++) {
            retVal.set(i, Integer.parseInt(inputs[i]));
        }
        return retVal;
    }

    public static void main(String[] args) {
        ArrayList<Example> examplesBuffer = new ArrayList<>();
        try {
            CSVReader reader = null;
            reader = new CSVReader(new FileReader("res/character_set.csv"));
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                MyMatrix output = charToOutputMatrix(nextLine[0].charAt(0));
                MyMatrix input = inputArrayToMatrix(Arrays.copyOfRange(nextLine, 1, nextLine.length));
                examplesBuffer.add(new Example(input, output));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int testSetSize = (int) (sizeOfTestSet * examplesBuffer.size());
        System.out.println("Test set size: " + testSetSize);
        Example[] testSet = new Example[testSetSize];
        testSet = examplesBuffer.subList(0, testSetSize).toArray(testSet);
        Example[] trainingSet = new Example[examplesBuffer.size() - testSetSize];
        trainingSet = examplesBuffer.subList(testSetSize, examplesBuffer.size()).toArray(trainingSet);
        MLP mlp = new MLP(16, 10, 26);
        System.out.println("Learning");
        mlp.learn(10000, trainingSet, 0.01, MyMatrix.LOGISTIC_SQAUSH);
        double error = 0;
        System.out.println("Testing");
        System.out.println("Size of test set: " + testSet.length);
        for (Example example : testSet) {
//            error += mlp.forward(example.input, MyMatrix.LOGISTIC_SQAUSH).minus(example.output).abs().elementSum();
            int outputLetter = mlp.forward(example.input, MyMatrix.LOGISTIC_SQAUSH).getIndexOfMax();
            int shouldBeLetter = example.output.getIndexOfMax();
            if (outputLetter != shouldBeLetter) {
                System.out.println("output: " + outputLetter + ", should be: " + shouldBeLetter);
                error += 1;
            }
        }
        System.out.println("Number of incorrect characters: " + error);
        System.out.println("Percentage error: " + error /testSetSize);
    }
}