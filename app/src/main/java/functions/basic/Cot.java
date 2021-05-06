package functions.basic;

import functions.Function;
import functions.meta.*;

//котангенс
public class Cot extends Trig{
    @Override
    public double getValueAt(double x) {
        return 1/Math.tan(x);
    }

    public Function derivative() {
        return new Div(new Const(-1), new Pow(new Sin(), new Const(2)));
    }

    @Override
    public String toString() {
        return "ctg(x)";
    }
}
