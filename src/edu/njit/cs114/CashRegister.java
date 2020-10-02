
package edu.njit.cs114;

import java.util.Arrays;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/9/20
 */
public class CashRegister {

    private int [] denominations;
    private final int INFINITY = Integer.MAX_VALUE;
    /**
     * Constructor
     * @param denominations2 values of coin types not in any order
     * @throws Exception when a coin of denomination 1 does not exist
     */
    public CashRegister(int [] denominations2) throws Exception {
        /**
         * Complete code here
         */
        Arrays.sort(denominations2);
        if(denominations2[0]!=1){
            throw new Exception();
        }
        else {
            denominations = Arrays.copyOf(denominations2, denominations2.length);
        }

    }

    /**
     * Make change for value
     * @param value
     * @return array of same length as denominations array that specifies
     *         coins of each denomination to use in making given change
     *         with minimum number of coins
     */
    public int [] makeChange(int value) {
        /**
         * Complete code here
         */
        return makeChange(0,value);
    }

    public int[] makeChange(int startDenomIndex, int remainingValue){
        if(startDenomIndex >= denominations.length){
            if(remainingValue>0){
                return initChangeArray(true);
            }
            else if(remainingValue == 0){
                return initChangeArray(false);
            }
        }else {
            int maxCoinsForStartDenom = remainingValue/denominations[startDenomIndex];
            int[] minCoinChanges = initChangeArray(true);
            for (int i = 0; i <= maxCoinsForStartDenom; i++) {
                int[] minRemCoinChanges = makeChange(startDenomIndex + 1, remainingValue - i * denominations[startDenomIndex]);
                if(numCoins(minRemCoinChanges) == INFINITY){
                }
                else if(i+numCoins(minRemCoinChanges)<numCoins(minCoinChanges)){
                    minCoinChanges = Arrays.copyOf(minRemCoinChanges, minRemCoinChanges.length);
                    minCoinChanges[startDenomIndex] = i;
                }
            }
            return minCoinChanges;
        }
        return denominations;//I was getting an error for the method
                             // not returning anything for some reason so I just put this here
    }

    private int numCoins(int [] changes) {
        int sum = 0;
        for (int i=0; i < changes.length; i++) {
            if (changes[i] == INFINITY) {
                return INFINITY;
            }
            sum += changes[i];
        }
        return sum;
    }

    private int [] initChangeArray(boolean allInfinty){
        if(allInfinty){
            int [] tempArray = new int[denominations.length];
            for(int i = 0; i<denominations.length; i++){
                tempArray[i] = INFINITY;
            }
            return tempArray;
        }
        else{
            int [] tempArray = new int[denominations.length];
            for(int i = 0; i<denominations.length; i++){
                tempArray[i] = 0;
            }
            return tempArray;
        }
    }


    /**
     * Specifies description of change in coins
     * @param coins
     * @return
     */
    public void printValues(int [] coins) {
        StringBuilder builder = new StringBuilder();
        int totalCoins = 0;
        for (int i=0; i < denominations.length; i++) {
            if (coins[i] > 0) {
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(coins[i] + " coins of value " + denominations[i]);
                totalCoins += coins[i];
            }
        }
        builder.append(" for a total of " + totalCoins + " coins");
        System.out.println(builder.toString());
    }

    public static void main(String [] args) throws Exception {
        CashRegister reg = new CashRegister(new int [] {50, 25, 10, 5, 1});
        System.out.println("Change for " + 48 + ":");
        int [] change = reg.makeChange(48);
        // should have a total of 6 coins
        reg.printValues(change);
        assert Arrays.stream(change).sum() == 6;
        System.out.println("Change for " + 56 + ":");
        change = reg.makeChange(56);
        // should have a total of 3 coins
        reg.printValues(change);
        assert Arrays.stream(change).sum() == 3;
        reg = new CashRegister(new int [] {25, 10, 1});
        System.out.println("Change for " + 33 + ":");
        change = reg.makeChange(33);
        // should have a total of 6 coins
        reg.printValues(change);
        assert Arrays.stream(change).sum() == 6;
        reg = new CashRegister(new int [] {1, 7, 24, 42});
        System.out.println("Change for " + 48 + ":");
        change = reg.makeChange(48);
        // should have a total of 2 coins
        reg.printValues(change);
        assert Arrays.stream(change).sum() == 2;
        reg = new CashRegister(new int [] {50, 1, 3, 16, 30});
        System.out.println("Change for " + 35 + ":");
        change = reg.makeChange(35);
        // should have a total of 3 coins
        reg.printValues(change);
        assert Arrays.stream(change).sum() == 3;
    }
}
