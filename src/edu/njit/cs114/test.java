package edu.njit.cs114;
import java.util.Random;

public class test {

    public static void main(String[] args){
            test test1 = new test();
            System.out.println(test1.piFinder(1000000000));
    }
    public double piFinder(int num){
        Random ran = new Random();
        int numInCircle = 0;
        int numNotInCircle = 0;
        for(int i = 0; i<=num; i++){
            double x = ran.nextDouble();
            double y = ran.nextDouble();
            if((x*x+y*y)<=1){
                numInCircle++;
            }
            numNotInCircle++;
        }

        return 4.0*numInCircle/numNotInCircle;

    }

}

