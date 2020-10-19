package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/13/20
 */
public class OperandToken implements ExpressionToken {

    private double value;

    public OperandToken(double val) {
        value = val;
    }

    public OperandToken(String str) {
        value = Double.parseDouble(str);
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return Double.toString(value);
    }

}
