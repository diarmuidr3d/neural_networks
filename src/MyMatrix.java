import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleBase;

/**
 * Created by Diarmuid Ryan.
 */
class MyMatrix extends SimpleBase<MyMatrix> {

    static final int LOGISTIC_SQAUSH = 1;
    static final int BIPOLAR_SQUASH = 2;

    MyMatrix(int numRows, int numCols) {
        super(numRows, numCols);
    }

    MyMatrix(double data[][]) {
        mat = new DenseMatrix64F(data);
    }

    @Override
    protected MyMatrix createMatrix(int numRows, int numCols) {
        return new MyMatrix(numRows,numCols);
    }

    MyMatrix abs() {
        MyMatrix retVal = new MyMatrix(numRows(), numCols());
        for(int index = 0; index < getNumElements(); index++) {
            retVal.set(index, Math.abs(get(index)));
        }
        return retVal;
    }

    MyMatrix squash(int squashFunction) {
        MyMatrix retVal = new MyMatrix(numRows(), numCols());
        for(int index = 0; index < getNumElements(); index++) {
            retVal.set(index, getSquashValue(squashFunction, get(index)));
        }
        return retVal;
    }

    private double getSquashValue(int squashFunction, double value) {
        switch (squashFunction) {
            case LOGISTIC_SQAUSH:
                return 1/(1 + Math.exp(-value));
            case BIPOLAR_SQUASH:
                return Math.tanh(value);
        }
        return 0;
    }

    MyMatrix derivativeSquash(int squashFunction) {
        MyMatrix retVal = new MyMatrix(numRows(), numCols());
        for(int index = 0; index < getNumElements(); index++) {
            retVal.set(index, getDerivedSquashValue(squashFunction, get(index)));
        }
        return retVal;
    }

    private double getDerivedSquashValue(int squashFunction, double value) {
        switch (squashFunction) {
            case LOGISTIC_SQAUSH:
                return value * (1 - value);
            case BIPOLAR_SQUASH:
                return 1/Math.pow(Math.cosh(value),2);
        }
        return 0;
    }

    MyMatrix multiplyIndividual(MyMatrix AnotherMatrix) {
        MyMatrix retVal = new MyMatrix(numRows(), numCols());
        for(int index = 0; index < getNumElements(); index++) {
            retVal.set(index, get(index) * AnotherMatrix.get(index));
        }
        return retVal;
    }

    int getIndexOfMax() {
        double currentMax = 0;
        int maxIndex = -1;
        for(int index = 0; index < getNumElements(); index++) {
            if (get(index) > currentMax) {
                currentMax = get(index);
                maxIndex = index;
            }
        }
        return maxIndex;
    }
}
