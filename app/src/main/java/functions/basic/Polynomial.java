package functions.basic;

import functions.Function;
//CE - многочлен
public class Polynomial implements Function {
    private Const[] coeff;
    private int degree;
    public Polynomial(double[] c) {
        coeff = new Const[c.length];
        for (int i = 0; i < coeff.length; ++i)
            coeff[i] = new Const(c[i]);
        degree = c.length - 1;
    }

    @Override
    public double getValueAt(double x) {
        double res = 0.;
        for (int i = 0; i < coeff.length; ++i) {
            res+= coeff[i].getValue()*Math.pow(x, degree - i);
        }
        return res;
    }

    public Function derivative() {
        if (degree == 1)
            return new Const(coeff[0].getValue());
        double [] new_coeff = new double [degree];
        for (int i = 0; i < degree; ++i) {
            new_coeff[i] = coeff[i].getValue()*(degree-i);
        }
        return new Polynomial(new_coeff);
    }

    public String toString() {
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i < coeff.length; ++i) {
            if (coeff[i].getValue()!=0) {
                if (coeff[i].getValue() > 0 && i != 0)
                    res.append(". + ");
                if (coeff[i].getValue() != 1.)
                    res.append(coeff[i].toString() + "*");
                if (degree - i > 0) {
                    res.append("x");
                    if (degree - i != 1)
                        res.append("^" + (degree - i));
                }
            }
        }
        return res.toString();
    }
}//\CE