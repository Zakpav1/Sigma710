package functions.basic;

import functions.Function;

//CE - гиперболический косинус
public class Cosh extends Hyper {
    @Override
    public double getValueAt(double x) {
        return Math.cosh(x);
    }

    public Function derivative() {
        return new Sinh();
    }

    @Override
    public String toString() {
        return "ch(x)";
    }
}//\CE