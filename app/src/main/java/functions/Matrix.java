package functions;
//CE - класс матрицы
public class Matrix {
    private double[][] matrix;
    private int size;
    public Matrix(double[][]m, int n) {
        matrix = m;
        size = n;
    }

    private void setElement(int i, int j, double a){
        matrix[i][j] = a;
    }
    private double getElement (int i, int j) { return matrix[i][j]; }

    public double det() {
        if (size==1)
            return matrix[0][0];
        if (size==2)
            return matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
        double det = 0;
        double[][] m = new double[size-1][size-1];
        double coef = 1;
        for (int k = 0; k < size; ++k){
            int move_j = 0;
            for (int i = 1; i < size; ++i){
                move_j = 0;
                for (int j = 0; j < size - 1; ++j){

                    if (j==k)
                        move_j = 1;
                    m[i - 1][j] = matrix[i][j + move_j];
                }
            }
            det+=coef*matrix[0][k]*(new Matrix(m, size-1)).det();
            coef*=-1;
        }
        return det;
    }

    public Matrix[] LU_decomposition() {
        //проверить вырожденность//
        for (int i = 0; i < size; ++i){
            double[][] check = new double[i+1][i+1];
            for (int k = 0; k < i + 1; ++k){
                for (int j = 0; j < i + 1; ++j){
                    check[k][j] = matrix[k][j];
                }
            }
            if (Math.abs((new Matrix(check, i+1)).det()) < Double.MIN_VALUE){
                throw new IllegalStateException("Нарушена невырожденность главных миноров! LU разложение невозможно!\nНулю равен минор " + (i+1) + " порядка!\n");
            }
        }
        Matrix[] LU = new Matrix[2];
        Matrix buffer = new Matrix(matrix, size);
        double[][] l = new double[size][size];
        double[][] u = new double[size][size];
        LU[0] = new Matrix(l, size);
        LU[1] = new Matrix(u, size);
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j){
                if (i==j)
                    LU[0].setElement(i,j,1.);
                else
                    LU[0].setElement(i,j,0.);
                LU[1].setElement(i,j,0.);
            }
        for (int i = 0; i < size; ++i) {
            double sub;
            for (int j = i; j < size; j++)
            {
                sub = 0;
                for (int k = 0; k < i; k++)
                    sub += buffer.getElement(i,k) * buffer.getElement(k, j);
                buffer.setElement(i,j, matrix[i][j] - sub);
            }
            for (int j = i + 1; j < size; j++)
            {
                sub = 0;
                for (int k = 0; k < i; k++)
                    sub += buffer.getElement(j,k) * buffer.getElement(k,i);
                buffer.setElement(j,i,(matrix[j][i]-sub)/buffer.getElement(i,i));
            }

        }
        for (int i = 0; i < size; ++i){
            for (int j = 0; j < size; ++j){
                if (i > j)
                    LU[0].setElement(i,j, buffer.getElement(i,j));
                else
                    LU[1].setElement(i,j, buffer.getElement(i,j));
            }
        }
        return LU;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                res.append(matrix[i][j])
                        .append(", ");
                if (j == size-1)
                    res.setCharAt(res.length()-2, ';');
            }
        }
        res.deleteCharAt(res.length()-1);
        res.deleteCharAt(res.length()-1);
        res.append(']');
        return res.toString();
    }
}//\CE