package functions.parsers;

import functions.Matrix;
//CE - парсер таблицы точек
public class TableParser {
    private static double[][] table;
    private static int n;
    public static double[][] parseTable(String string) {
        string = string.replaceAll(" ", "");
        if (checkBrackets(string) && check_ptrs(string)) {
            table = new double[2][n];
            if (checkNumbers(string))
                return table;
        }
        throw new IllegalArgumentException("Неверный формат! (Wrong format!)");
    }


    private static boolean checkBrackets(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (i==0 && string.charAt(i)!='[')
                return false;
            if (i==string.length()-1 && string.charAt(i)!=']')
                return false;
            if (i > 0 && i < string.length()-1)
                if (string.charAt(i)=='[' || string.charAt(i)==']')
                    return false;
        }
        return true;
    }

    private static boolean check_ptrs(String string) {
        int n1 = 0; //количество точек с запятой
        int n2 = 0; //количество запятых в одном блоке
        int n3 = 0; //для проверки равенства количества запятых в блоках
        int n4 = 0; //всего запятых
        for (int i = 1; i < string.length(); ++i) {
            if (string.charAt(i)==';') {
                if (i==string.length()-2 || i==1)
                    return false;
                ++n1;
                if (n1 == 1)
                    n3 = n2;
                if (n2 != n3)
                    return false;
                n3 = n2;
                n2 = 0;
                if (string.charAt(i-1)==',')
                    return false;
            }
            if (string.charAt(i)==',') {
                if (i==string.length()-2 || i==1)
                    return false;
                ++n2;
                ++n4;
                if (string.charAt(i-1)==',' || string.charAt(i-1)==';')
                    return false;
            }
            if (i==string.length()-1)
                if (n2!=n3 || n1!=n4-1)
                    return false;
        }
        if (n4 < 3 )
            return false;
        n = n4;
        return true;
    }

    private static boolean checkNumbers(String string) {
        StringBuilder numb = new StringBuilder();
        int col = 0;
        int raw = 0;
        int added = 0;
        for (int i = 1; i < string.length(); ++i) {
            if (string.charAt(i) != ',' && string.charAt(i) != ';' && i + 1 != string.length())
                numb.append(string.charAt(i));
            else {
                try {
                    double d = Double.parseDouble(numb.toString());
                    numb = new StringBuilder();
                    if (added > 0 && raw == 0)
                    {
                        for (int j = 0; j < added; ++j) {
                            if (Math.abs(table[0][j] - d) < Double.MIN_VALUE)
                                return false;
                        }
                    }
                    table[raw][col] = d;
                    if (raw==0)
                        ++added;
                } catch (NumberFormatException e) {
                    return false;
                }
                if (raw == 1) {
                    raw = 0;
                    ++col;
                } else
                    ++raw;
            }
        }
        return true;
    }
}//\CE