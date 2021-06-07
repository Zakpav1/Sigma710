package functions.basic;

import functions.Function;
import functions.meta.Signum;
//CE - интерфейс функции
public class Abs implements Function {
    @Override
    public double getValueAt(double x) {
        return Math.abs(x);
    }

    @Override
    public Function derivative() {
        return new Signum(new Polynomial(new double[]{1,0}));
    }

    @Override
    public String toString() {
        return "|x|";
    }
}//\CE