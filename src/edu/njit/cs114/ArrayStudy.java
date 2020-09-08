package edu.njit.cs114;

import java.util.Arrays;

/**
 * Author:
 * Date created:
 */
public class ArrayStudy {

    public static double [] [] manipulate(double [] [] a) {
        // Perform step (a)
        double [] [] a1 = extract(a);
        System.out.println("Content of array after step (a)");
        printArray(a1);
        double [] [] a2 = replace(a1);
        System.out.println("Content of array after step (b)");
        printArray(a2);
        return a2;
    }

    // prints 2D array
    private static void printArray(double [] [] a) {
        for (int i=0; i< a.length; i++) {
            for (int j=0; j< a[i].length;j++) {
                System.out.print(a[i][j]+",");
            }
            System.out.println();
        }
    }

    private static double [][] extract (double [][] a) {
        int halfCol = 0;
        int noOfNegativesInRow =0;
        int noOfRowsRemoved = 0;
        boolean [] removeRow = new boolean[a.length];
        // Loop through each row of a to check if number of negatives is at least halfCol
        for (int i =0; i < a.length; i++) {
            halfCol = a[i].length / 2;
            noOfNegativesInRow = 0;

            /**
             * To be completed
             *   Loop through each column of the i-th row to update noOfNegativesInRow
             *   If noOfNegativesInRow is at least halfCol, set removeRow[i] to true
             *   and also update noOfRowsRemoved
             */

            for(int p = 0; p<a[i].length; p++){
                if(a[i][p]<0){
                    noOfNegativesInRow++;
                }
            }
            if(noOfNegativesInRow>=halfCol){
                removeRow[i] = true;
                noOfRowsRemoved++;
            }
        }


        double [][] b = new double[a.length-noOfRowsRemoved][];
        int validRowIndex = 0;
        /**
         * To be completed
         *  Loop through each row i of a and if removeRow[i] is false, add it to b
         *  using Arrays.copyOf() function
         */
        int count = 0;
        for(int q = 0; q<a.length; q++){
            if(!removeRow[q]){
                b[count] = Arrays.copyOf(a[q], a[q].length);
                count++;
            }
        }

        return b;
    }


    private static double [][] replace(double [][] b) {
        double columnSum = 0.0; //Column sum
        double columnAverage = 0.0;//Column average
        int numNonNegatives = 0; // number of positives in the column
        //Loop through each column j to replace negative with average of positives in the same column
        for (int j=0; j < b[0].length; j++) {
            columnSum = 0.0;
            numNonNegatives = 0;
            /**
             * To be completed
             *  First find columnSum and numNonNegatives using non-negative values of
             *  the j-th column of b.
             *   This is done by looping through each row of the same column
             */

            for(int k = 0; k<b.length; k++) {
                if(b[k][j]>=0){
                    columnSum+=b[k][j];
                    numNonNegatives++;
                }
            }

            columnAverage = numNonNegatives > 0 ? columnSum/numNonNegatives : 0;
            /**
             *  To be completed
             *  Now replace all negative numbers of j-th column of b with the column average
             */

            for(int k = 0; k<b.length; k++) {
                if(b[k][j]<0){
                    b[k][j] = columnAverage;
                }
            }

        }
        return b;
    }


    //-----------------------------------------------------------------
    //
    //-----------------------------------------------------------------
    public static void main (String[] args)
    {
        double [][] a ={{-1,4,3,2,-3,2},{-2,3,5,-4,0,1},{-1,-3,4,1,-1,0},{-1,2,-3,6,5,3},{-3,2,-3,-5,0,0}}; //A 4 x 5 Dinmension;

        System.out.println("Number of rows of the 2D array is " + a.length);

        double [] [] b = manipulate(a);


    }

}




