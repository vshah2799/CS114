package edu.njit.cs114;

public class test {
    public static void main(String[] args){
        System.out.print(compute(21,15));
    }
    public static int compute(int x, int y) {
        if (x == 0) {
            return y;
        } else if (y < x) {
            System.out.println("Test");
            return compute(y, x);
        } else {
            System.out.println("Test");
            return compute(y % x, x);
        }
    }
}

