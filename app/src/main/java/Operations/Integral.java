package Operations;

import functions.Function;
import functions.meta.Div;

public class Integral {
    public static double integrate(Function f, double a, double b) {
        if (a > b)
            throw new IllegalStateException("Неверные пределы интегрирования! (Wrong limits of integration!)");
        return (a < b)? Runge_rule(f, a, b): 0;
    }
    private static double Sympson(Function f, double a, double b, int n) {
        double res = 0;
        double left = a;
        double step = (b-a)/n;
        double right = a + step;
        for (int i = 0; i < n; ++i) {
            if (Double.isInfinite(res)) {
                return res;
            }
            res += (right - left) / 6 * (f.getValueAt(left) + 4 * f.getValueAt((left + right) / 2) + f.getValueAt(right));
            left = right;
            right+=step;
        }
        return res;
    }
    private static double Runge_rule(Function f, double a, double b) {
        int n = (int) (b-a)*100;
        double s1 = Sympson(f,a,b,n);
        if (Double.isInfinite(s1)) {
            System.out.println("Введён несобственный интеграл!");
            return s1;
        }
        if (Double.isNaN(s1)) {
            System.out.println("Промежуток интегрирования выходит за границы области определения!");
            return s1;
        }
        double s2 = Sympson(f,a,b,2*n);
        if (Double.isInfinite(s2)) {
            System.out.println("Введён несобственный интеграл!");
            return s1;
        }
        if (Double.isNaN(s2)) {
            System.out.println("Промежуток интегрирования выходит за границы области определения!");
            return s1;
        }
        while (Math.abs(s1-s2)/15 > Double.MIN_VALUE) {
            n *= 2;
            s1 = s2;
            s2 = Sympson(f,a,b,n);
            if (Double.isInfinite(s2)) {
                System.out.println("Введён несобственный интеграл!");
                return s1;
            }
            if (Double.isNaN(s2)) {
                System.out.println("Промежуток интегрирования выходит за границы области определения!");
                return s1;
            }
        }
        return s2;
    }
}
