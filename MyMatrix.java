import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleBase;

/**
 * Created by Diarmuid Ryan.
 */
class MyMatrix extends SimpleBase<MyMatrix> {

    public static final int LOGISTIC_SQAUSH = 1;
    public static final int BIPOLAR_SQUASH = 2;

    MyMatrix(int numRows, int numCols) {
        super(numRows, numCols);
    }

    public MyMatrix(double data[][]) {
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
//                return (1 - Math.exp(-2 * value))/(1 + Math.exp(-2 * value));
                return Math.tanh(value);
//                return (2 / (1 + Math.exp(-value))) - 1;
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
//                double squashValue = getSquashValue(squashFunction, value);
//                return (1 / 2) * (1 + value) * (1 - value);
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
}
