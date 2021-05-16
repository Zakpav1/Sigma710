package Operations;

public class Approximation {
    private static double a, b;
    private static Approximation instance = new Approximation();
    private Approximation() {
    }
    public static Approximation getInstance() {
        return instance;
    }
    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }
    public static void LS_linear(double[] x, double[] y) {
        double x_mid = 0.;
        double y_mid = 0.;
        for (int i = 0; i < x.length; ++i) {
            x_mid += x[i];
            y_mid += y[i];
        }
        x_mid/=x.length;
        y_mid/=y.length;
        double nom = 0.;
        double denom = 0.;
        for (int i = 0; i < x.length; ++i) {
            nom += (x[i]-x_mid)*(y[i]-y_mid);
            denom += (x[i]-x_mid)*(x[i]-x_mid);
        }
        b = nom/denom;
        a = y_mid - b * x_mid;
    }
    public double getValueAt(double x) {
        return a+b*x;
    }
}
