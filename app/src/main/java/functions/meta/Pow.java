package functions.meta;

import functions.Function;
import functions.basic.Const;
import functions.basic.Log;

//CE - возведение в степень
public class Pow implements Function {
    private final Function f, g;
    public Pow(Function f1, Function g1) {
        f = f1;
        g = g1;
    }
    @Override
    public double getValueAt(double x) {
        return Math.pow(f.getValueAt(x), g.getValueAt(x));
    }

    @Override
    public Function derivative() {
        if (g instanceof Const)
            return new Mult (new Mult(new Const(((Const) g).getValue()), new Pow(f, new Const(((Const)g).getValue()-1))), f.derivative());
        return new Mult(new Pow (f, g), new Sum(new Mult(g.derivative(), new Comp (new Log(Math.E), f)), new Div(new Mult(g, f.derivative()), f)));

    }

    public Function getF() {
        return f;
    }

    public Function getG() {
        return g;
    }

    @Override
    public String toString() {
        return "(" + f.toString() + ")" + "^" + "(" + g.toString() + ")";
    }
}//\CE