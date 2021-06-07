package functions.meta;
//CE - частное функций
import functions.Function;
import functions.basic.Const;

public class Div implements Function {
    private final Function f, g;
    public Div(Function f1, Function g1) {
        f = f1;
        g = g1;
    }
    @Override
    public double getValueAt(double x) {
        if (g.getValueAt(x) != 0)
            return f.getValueAt(x) / g.getValueAt(x);
        else {
            System.out.println("Деление на 0! (Division by zero)");
            return f.getValueAt(x) < 0? Double.NEGATIVE_INFINITY: Double.POSITIVE_INFINITY;
        }

    }

    public Function derivative () {
        return new Div (new Sub (new Mult(f.derivative(), g), new Mult(f, g.derivative())), new Pow(g, new Const(2)));
    }

    public Function getF() {
        return f;
    }

    public Function getG() {
        return g;
    }

    @Override
    public String toString() {
        return "(" + f.toString() + ")" + " / " + "(" + g.toString() + ")";
    }
}//\CE