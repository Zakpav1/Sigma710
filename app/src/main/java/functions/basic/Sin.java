package functions.basic;

import functions.Function;

//CE - синус
public class Sin extends Trig{
    @Override
    public double getValueAt(double x) {
        return Math.sin(x);
    }

    public Function derivative() {
        return new Cos();
    }

    @Override
    public String toString() {
        return "sin(x)";
    }
}//\CE