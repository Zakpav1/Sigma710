package functions.basic;

import functions.Function;
import functions.meta.*;

//CE - гиперболический котангенс
public class Coth extends Hyper{
    @Override
    public double getValueAt(double x) {
        return 1/Math.tanh(x);
    }

    public Function derivative() {
        return new Div (new Const(-1), new Pow(new Sinh(), new Const(-2)));
    }

    @Override
    public String toString() {
        return "cth(x)";
    }
}//\CE