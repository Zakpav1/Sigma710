package functions.basic;
//CE - логарифм
import functions.Function;
import functions.meta.Mult;
import functions.meta.Pow;

public class Log implements Function {
    private Const base;
    public Log(double a) {
        if (a>0 && a!=1)
            base = new Const(a);
        else
            throw new IllegalArgumentException("Недопустимое основание! (Wrong base!)");
    }

    @Override
    public double getValueAt(double x) {
        return Math.log(x)/Math.log(base.getValue());
    }

    public Function derivative() {
        if (Math.abs(base.getValue()-Math.E)<Double.MIN_VALUE)
            return new Pow(new Polynomial(new double[]{1,0}), new Const(-1));
        return new Mult(new Const((new Log(base.getValue())).getValueAt(Math.E)),new Pow(new Polynomial(new double[]{1,0}), new Const(-1)));
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("");
        if (base.getValue() == Math.E)
            res.append("ln");
        else
            res.append("log_" + base.toString());
        res.append("(x)");
        return res.toString();
    }
}//\CE