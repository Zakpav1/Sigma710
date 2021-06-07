package functions.basic;

import functions.Function;
import functions.meta.Pow;

//CE - тангенс
public class Tan extends Trig{
    @Override
    public double getValueAt(double x) {
        if (Math.abs(Math.cos(x) - Math.cos(Math.PI/2)) < 1.e-4)
            return Double.POSITIVE_INFINITY;
        return Math.tan(x);
    }

    public Function derivative() {
        return new Pow(new Cos(), new Const(-2));
    }

    @Override
    public String toString() {
        return "tg(x)";
    }
}//\CE