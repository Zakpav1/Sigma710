package functions.meta;
//CE - композиция - сложная функция
import functions.Function;

public class Comp implements Function {
    private final Function f, g;
    public Comp(Function f1, Function g1) {
        f = f1;
        g = g1;
    }
    @Override
    public double getValueAt(double x) {
        return f.getValueAt(g.getValueAt(x));
    }

    public Function getF() {
        return f;
    }

    public Function getG() {
        return g;
    }

    public Function derivative () {
        return new Mult (new Comp(f.derivative(), g), g.derivative());
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(f.toString());
        for (int i = 0; i < res.length(); ++i) {
            if (res.charAt(i)=='x') {
                if ((i==0 && res.charAt(i+1)!=')') || (i>0 && res.charAt(i-1)!='('))
                    res.replace(i, i+1, "(" + g.toString() + ")");
                else
                    res.replace(i, i+1, g.toString());
                i+=g.toString().length();
            }
        }
        return res.toString();
    }
}//\CE