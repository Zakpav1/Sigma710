package functions.basic;

import functions.Function;
import functions.meta.Pow;

//гиперболический тангенс
public class Tanh extends Hyper{
    @Override
    public double getValueAt(double x) {
        return Math.tanh(x);
    }

    public Function derivative() {
        return new Pow(new Cosh(), new Const(-2));
    }

    @Override
    public String toString() {
        return "th(x)";
    }
}
