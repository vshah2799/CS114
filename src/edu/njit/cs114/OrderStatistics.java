package edu.njit.cs114;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/29/20
 */
public class OrderStatistics {

    private static Random random = new Random();

    private static void swap(int [] arr, int k1, int k2) {
        int temp = arr[k1];
        arr[k1] = arr[k2];
        arr[k2] = temp;
    }

    /**
     * Return the index pivIndex of the pivot element after partitioning sub array
     * After partitioning left sub array will be in dataArr[fromIndex..pivIndex-1]
     *  right sub array will be in dataArr[pivIndex...toIndex-1]
     * @param dataArr
     * @param fromIndex starting index of sub array
     * @param toIndex ending index (not including) of sub array i.e sub array is given
     *                by dataArr[fromIndex..toIndex-1]
     * @param pivotElementIdx index of pivot element before partitioning
     * @return
     */
    public static int partition(int [] dataArr, int fromIndex, int toIndex,
                                int pivotElementIdx) {
        /**
         * Complete the code here
         */
        int pivotElement = dataArr[pivotElementIdx];
        swap(dataArr, fromIndex, pivotElementIdx);
        int leftPointer = fromIndex + 1;
        int rightPointer = toIndex-1;

        do{
            while(leftPointer<toIndex && dataArr[leftPointer]<pivotElement){
                leftPointer++;
            }
            while(rightPointer>=fromIndex && dataArr[rightPointer]>=pivotElement){
                rightPointer--;
            }
            if(leftPointer<rightPointer){
                swap(dataArr, leftPointer, rightPointer);
                leftPointer++;
                rightPointer--;
            }
        }while(leftPointer<=rightPointer);

        if(rightPointer>=fromIndex){
            swap(dataArr, fromIndex, rightPointer);
            return rightPointer;
        }else{
            return fromIndex;
        }

    }

    public static int randomPartition(int [] dataArr, int fromIndex, int toIndex) {
        int randomOffset = random.nextInt(toIndex-fromIndex);
        return partition(dataArr, fromIndex, toIndex, fromIndex+randomOffset);
    }


    /**
     * Find k-th smallest element of the subarray dataArr[fromIndex..toIndex-1] using a random pivot
     * @param dataArr
     * @param fromIndex
     * @param toIndex
     * @param k
     * @return
     */
    private static int kthSmallestRandomPivot(int [] dataArr, int fromIndex, int toIndex, int k) {
        // complete the code here

        if(toIndex-fromIndex == 1){
            return dataArr[fromIndex];
        }
        int pivotPos = randomPartition(dataArr, fromIndex, toIndex);

        if(k<=pivotPos-fromIndex){
            return kthSmallestRandomPivot(dataArr, fromIndex, pivotPos, k);
        }else if(k == pivotPos-fromIndex+1){
            return dataArr[pivotPos];
        }else{
            return kthSmallestRandomPivot(dataArr, pivotPos+1, toIndex, k-(pivotPos-fromIndex+1));
        }
    }

    /**
     * Find k-th smallest element using a random pivot
     * @param dataArr
     * @param k
     * @return
     */
    public static int kthSmallestRandomPivot(int [] dataArr, int k) {
        return kthSmallestRandomPivot(dataArr, 0, dataArr.length, k);
    }

    public static int [] readIncomeData(String fileName) throws Exception {
        BufferedReader rdr = null;
        ArrayList<Integer> lst = new ArrayList<>();
        try {
            rdr = new BufferedReader(new FileReader(fileName));
            String line = null;
            while (((line=rdr.readLine())) != null) {
                lst.add(Integer.parseInt(line.trim()));
            }
        } finally {
            rdr.close();
        }
        int [] arr = new int[lst.size()];
        for (int i=0; i < arr.length; i++) {
            arr[i] = lst.get(i);
        }
        return arr;
    }

    public static void main(String [] args) throws Exception {
        int [] arr = new int [] {10,5,4,15,3,7,8,20,3,11,21,16};
        int medianVal = kthSmallestRandomPivot(arr,(int) Math.ceil(arr.length/2));
        System.out.println("median value of [10,5,4,15,3,7,8,20,3,11,21,16] = " + medianVal); // should be 8
        int eighthSmallest = kthSmallestRandomPivot(arr,8);
        System.out.println("eighthSmallest value of [10,5,4,15,3,7,8,20,3,11,21,16] = " + eighthSmallest); // should be 11
        int fourthSmallest = kthSmallestRandomPivot(arr,4);
        System.out.println("fourthSmallest value of [10,5,4,15,3,7,8,20,3,11,21,16] = " + fourthSmallest); // should be 5
        int [] incomes = readIncomeData("Incomes.txt");
        long startMillis = System.currentTimeMillis();
        int medianIncome = kthSmallestRandomPivot(incomes, (int) Math.ceil(incomes.length/2));
        long endMillis = System.currentTimeMillis();
        System.out.println("Median income (using selection) = " + medianIncome);
        System.out.println("Time(ms) for finding median using selection alg.:" + (endMillis-startMillis));
        startMillis = System.currentTimeMillis();
        int topOnePercentIncome = kthSmallestRandomPivot(incomes, (int) (0.99*incomes.length));
        endMillis = System.currentTimeMillis();
        System.out.println("Top one percent income (using selection) = " + topOnePercentIncome);
        System.out.println("Time(ms) for finding top one percent income using selection alg.:" + (endMillis-startMillis));
        startMillis = System.currentTimeMillis();
        int bottomQuarterIncome = kthSmallestRandomPivot(incomes, (int) (0.25*incomes.length));
        endMillis = System.currentTimeMillis();
        System.out.println("Bottom 25 percent income (using selection) = " + bottomQuarterIncome);
        System.out.println("Time(ms) for finding bottom 25 percent income using selection alg.:" + (endMillis-startMillis));
        int [] incomes1 = Arrays.copyOf(incomes, incomes.length);
        startMillis = System.currentTimeMillis();
        Arrays.sort(incomes1);
        medianIncome = incomes1[(int) Math.ceil(incomes1.length/2)-1];
        endMillis = System.currentTimeMillis();
        System.out.println("Median income (using sorting) = " + medianIncome);
        System.out.println("Top one percent income (using sorting) = " +
                incomes1[((int) (0.99*incomes1.length))-1]);
        System.out.println("Bottom 25 percent income (using sorting) = " +
                incomes1[((int) (0.25*incomes1.length))-1]);
        System.out.println("Time(ms) for finding median etc. using sorting :" + (endMillis-startMillis));
    }
}

