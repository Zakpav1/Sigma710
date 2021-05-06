package functions.basic;

import functions.Function;
import functions.meta.Mult;
import functions.meta.Pow;

//арккосинус
public class Acos implements Function {
    @Override
    public double getValueAt(double x) {
        return Math.acos(x);
    }

    @Override
    public Function derivative() {
        return new Mult(new Const(-1),new Pow(new Polynomial(new double[]{-1,0,1}), new Const(-0.5)));
    }

    @Override
    public String toString() {
        return "arccos(x)";
    }
}
