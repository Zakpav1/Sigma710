package functions.basic;

import functions.Function;

//гиперболический синус
public class Sinh extends Hyper{
    @Override
    public double getValueAt(double x) {
        return Math.sinh(x);
    }

    public Function derivative() {
        return new Cosh();
    }

    @Override
    public String toString() {
        return "sh(x)";
    }
}
