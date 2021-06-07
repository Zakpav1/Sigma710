package functions.basic;
//CE - показательная функция
import functions.Function;
import functions.meta.Mult;

public class Exp implements Function {
    private Const base;

    public Exp(double a) {
        if (a>0 && a!=1)
            base = new Const(a);
        else
            throw new IllegalArgumentException("Недопустимое основание! (Wrong base!)");
    }
    @Override
    public double getValueAt(double x) {
        if (x == 0)
            return 1;
        if (x == 1)
            return base.getValue();
        return Math.pow(base.getValue(), x);
    }

    public Function derivative() {
        if (Math.abs(base.getValue()-Math.E)<Double.MIN_VALUE)
            return new Exp(Math.E);
        return new Mult(new Const((new Log(Math.E)).getValueAt(base.getValue())), new Exp(base.getValue()));
    }

    public String toString() {
        StringBuilder res = new StringBuilder("");
        res.append(base.toString() + "^(x)");
        return res.toString();
    }
}//\CE