package functions.parsers;

import functions.Graph;
import functions.Matrix;
//CE - парсер матрицы
public class MatrixParser {
    private String expression;
    private int n;
    private double [][] m;

    public MatrixParser (String s) {
        expression = s;
    }

    public Matrix parseMatrix() {
        expression = expression.replaceAll(" ", "");
        if (checkBrackets() && check_ptrs()) {
            m = new double[n][n];
            if (checkNumbers())
                return new Matrix(m, n);
        }
        throw new IllegalArgumentException("Неверный формат! (Wrong format!)");
    }

    private boolean checkBrackets() {
        for (int i = 0; i < expression.length(); ++i) {
            if (i==0 && expression.charAt(i)!='[')
                return false;
            if (i==expression.length()-1 && expression.charAt(i)!=']')
                return false;
            if (i > 0 && i < expression.length()-1)
                if (expression.charAt(i)=='[' || expression.charAt(i)==']')
                    return false;
        }
        return true;
    }

    private boolean check_ptrs() {
        int n1 = 0; //количество точек с запятой
        int n2 = 0; //количество запятых в одном блоке
        int n3 = 0; //для проверки равенства количества запятых в блока
        for (int i = 1; i < expression.length(); ++i) {
            if (expression.charAt(i)==';') {
                if (i==expression.length()-2 || i==1)
                    return false;
                ++n1;
                if (n1 == 1)
                    n3 = n2;
                if (n2 != n3)
                    return false;
                n3 = n2;
                n2 = 0;
                if (expression.charAt(i-1)==',')
                    return false;
            }
            if (expression.charAt(i)==',') {
                if (i==expression.length()-2 || i==1)
                    return false;
                ++n2;
                if (expression.charAt(i-1)==',' || expression.charAt(i-1)==';')
                    return false;
            }
            if (i==expression.length()-1)
                if (n1!=n2 || n2!=n3)
                    return false;
        }
        n = n1 + 1;
        return true;
    }

    private boolean checkNumbers() {
        StringBuilder numb = new StringBuilder();
        int col = 0;
        int raw = 0;
        for (int i = 1; i < expression.length(); ++i) {
            if (expression.charAt(i) != ',' && expression.charAt(i) != ';' && i + 1 != expression.length())
                numb.append(expression.charAt(i));
            else {
                try {
                    double d = Double.parseDouble(numb.toString());
                    numb = new StringBuilder();
                    m[col][raw] = d;
                } catch (NumberFormatException e) {
                    return false;
                }
                if (raw == n-1) {
                    raw = 0;
                    ++col;
                } else
                    ++raw;
            }
        }
        return true;
    }
}//\CE