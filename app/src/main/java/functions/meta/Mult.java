package functions.meta;

import functions.Function;
import functions.basic.Const;

//CE - произведение функций
public class Mult implements Function {
    private final Function f, g;
    public Mult(Function f1, Function g1) {
        f = f1;
        g = g1;
    }
    @Override
    public double getValueAt(double x) {
        return f.getValueAt(x) * g.getValueAt(x);
    }

    public Function getF() {
        return f;
    }

    public Function getG() {
        return g;
    }

    public Function derivative () {
        return new Sum (new Mult(f.derivative(), g), new Mult(f, g.derivative()));
    }

    @Override
    public String toString() {
        if(f instanceof Const && g instanceof Const) {
            StringBuilder s = new StringBuilder();
            s.append((((Const) f).getValue() * ((Const) g).getValue()));
            return s.toString();
        }
        if (f instanceof Const && ((Const) f).getValue()==0)
            return "0";
        if (g instanceof Const && ((Const) g).getValue()==0)
            return "0";
        return "(" + f.toString() + ")" + " * " + "(" + g.toString() + ")";
    }
}//\CE