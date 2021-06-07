package functions.basic;

import functions.Function;
//CE - константа - некое число
public class Const implements Function {

    private double value;
    public Const(double c) {
        value = c;
    }
    public double getValue() {
        return value;
    }
    public double getValueAt(double x) {
        return value;
    }

    public Function derivative() {
        return new Const(0);
    }

    public String toString() {
        if (Math.abs(Math.abs(value) - Math.E) < Double.MIN_VALUE)
            if (value > 0)
                return "e";
            else
                return "-e";
        if (Math.abs(Math.abs(value) - Math.PI) < Double.MIN_VALUE)
            if (value > 0)
                return "pi";
            else
                return "-pi";
        return Double.toString(value);
    }
}//\CE