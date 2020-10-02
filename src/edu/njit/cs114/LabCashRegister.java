package edu.njit.cs114;


import java.util.Arrays;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/11/20
 */
public class LabCashRegister {

    private static final int INFINITY = Integer.MAX_VALUE;

    private int [] denominations;


    /**
     * Constructor
     * @param tempDenominations values of coin types not in any order
     * @throws Exception when a coin of denomination 1 does not exist
     */
    public LabCashRegister(int [] tempDenominations) throws Exception {
        /**
         * Complete code here
         */

        Arrays.sort(tempDenominations);
        if(tempDenominations[0]!=1){
            throw new Exception();
        }
        else {
            denominations = Arrays.copyOf(tempDenominations, tempDenominations.length);
        }
    }

    /**
     * Recursive function that finds the minimum number of coins to make change for the given
     * value using only denominations that are in indices
     * startDenomIndex, startDenomIndex+1,.....denomonations.length-1 of the denominations array
     * @param startDenomIndex
     * @param remainingValue
     * @return
     */
    int count1;
    private int minimumCoinsForChange(int startDenomIndex, int remainingValue) {
        /**
         * Complete code
         */
        if(startDenomIndex == 0){
            count1 += remainingValue/denominations[startDenomIndex];
            return count1;
        }
        else{
            count1 += remainingValue/denominations[startDenomIndex];
            int tempRemainingValue = remainingValue%denominations[startDenomIndex];
            minimumCoinsForChange(startDenomIndex-1, tempRemainingValue);
        }
        return count1;
    }

    /**
     * Wrapper function that finds the minimum number of coins to make change for the given value
     * @param value value for which to make change
     * @return
     */
    int count = 0;
    public int minimumCoinsForChange(int value) {
        /**
         * Complete code here
         */
        for(int i = denominations.length-1; i>=0;i--){
            int tempNum = minimumCoinsForChange(i,value);
            if(i==denominations.length-1){
                count = tempNum;
            }
            else if(tempNum<count){
                count = tempNum;
            }

        }

        count1 = 0;
        return count;

    }

    public static void main(String [] args) throws Exception {
        LabCashRegister reg = new LabCashRegister(new int [] {50, 25, 10, 5, 1});
        // should have a total of 6 coins
        int nCoins = reg.minimumCoinsForChange(48);
        System.out.println("Minimum coins to make change for " + 48
                + " from {50,25,10,5,1} = "+ nCoins);
        assert nCoins == 6;

        // should have a total of 3 coins
        nCoins = reg.minimumCoinsForChange(56);
        System.out.println("Minimum coins to make change for " + 56
                + " from {50,25,10,5,1} = "+ nCoins);
        assert nCoins == 3;

        reg = new LabCashRegister(new int [] {25, 10, 1});
        // should have a total of 6 coins
        nCoins = reg.minimumCoinsForChange(33);
        System.out.println("Minimum coins to make change for " + 33
                + " from {25,10,1} = "+ nCoins);
        assert nCoins == 6;
        reg = new LabCashRegister(new int [] {1, 7, 24, 42});
        // should have a total of 2 coins
        nCoins = reg.minimumCoinsForChange(48);
        System.out.println("Minimum coins to make change for " + 48
                + " from {1,7,24,42} = "+ nCoins);
        assert nCoins == 2;
        reg = new LabCashRegister(new int [] {50, 1, 3, 16, 30});
        // should have a total of 3 coins
        nCoins = reg.minimumCoinsForChange(35);
        System.out.println("Minimum coins to make change for " + 35
                + " from {50,1,3,16,30} = "+ nCoins);
        assert nCoins == 3;
    }
}

