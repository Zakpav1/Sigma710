package functions.basic;

import functions.Function;
import functions.meta.Mult;

//косинус
public class Cos extends Trig{
    @Override
    public double getValueAt(double x) {
        return Math.cos(x);
    }

    public Function derivative() {
        return new Mult(new Const(-1), new Sin());
    }

    @Override
    public String toString() {
        return "cos(x)";
    }
}
