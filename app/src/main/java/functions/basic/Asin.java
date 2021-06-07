package functions.basic;

import functions.Function;
import functions.meta.Pow;

//CE - арксинус
public class Asin implements Function {
    @Override
    public double getValueAt(double x) {
        return Math.asin(x);
    }

    @Override
    public Function derivative() {
        return new Pow(new Polynomial(new double[]{-1,0,1}), new Const(-0.5));
    }

    @Override
    public String toString() {
        return "arcsin(x)";
    }
}//\CE