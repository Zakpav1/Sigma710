package functions.basic;

import functions.Function;
import functions.meta.Div;

//CE - арктангенс
public class Atan implements Function {
    @Override
    public double getValueAt(double x) {
        return Math.atan(x);
    }

    @Override
    public Function derivative() {
        return new Div(new Const(1), new Polynomial(new double[]{1,0,1}));
    }

    @Override
    public String toString() {
        return "arctg(x)";
    }
}//\CE