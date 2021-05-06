package functions.basic;

import functions.Function;
import functions.meta.Div;
import functions.meta.Mult;
import functions.meta.Pow;

//арккотангенс
public class Acot implements Function {
    @Override
    public double getValueAt(double x) {
        return Math.PI - Math.atan(x);
    }

    @Override
    public Function derivative() {
        return new Div(new Const(-1), new Polynomial(new double[]{1,0,1}));
    }

    @Override
    public String toString() {
        return "arcctg(x)";
    }
}
