import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleBase;

/**
 * Created by Diarmuid Ryan.
 */
class MyMatrix extends SimpleBase<MyMatrix> {

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

    MyMatrix sigmuoidSquash() {
        MyMatrix retVal = new MyMatrix(numRows(), numCols());
        for(int index = 0; index < getNumElements(); index++) {
            retVal.set(index, 1/(1 + Math.exp(-get(index))));
        }
        return retVal;
    }

    MyMatrix alternativeSquash() {
        MyMatrix retVal = new MyMatrix(numRows(), numCols());
        for(int index = 0; index < getNumElements(); index++) {
            retVal.set(index, get(index) * ( 1 - get(index)));
        }
        return retVal;
    }

    MyMatrix multiplyIndividual(MyMatrix AnotherMatrix) {
        MyMatrix retVal = new MyMatrix(numRows(), numCols());
        for(int index = 0; index < getNumElements(); index++) {
            retVal.set(index, get(index) * AnotherMatrix.get(index));
        }
        return retVal;
    }
}
