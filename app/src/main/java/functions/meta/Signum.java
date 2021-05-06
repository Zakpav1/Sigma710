package functions.meta;

import functions.Function;
import functions.basic.Const;

// сигнум функции
public class Signum implements Function {
    private final Function f;
    public Signum(Function f1) {
        f = f1;
    }
    @Override
    public double getValueAt(double x) {
        if (f.getValueAt(x) > 0)
            return 1;
        else if (f.getValueAt(x) < 0)
            return -1;
        return 0;
    }

    @Override
    public Function derivative() {
        return new Const(0);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("sgn(x)");
        res.replace(res.length()-2, res.length()-1, f.toString());
        return res.toString();
    }
}
