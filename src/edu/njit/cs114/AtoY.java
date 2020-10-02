package edu.njit.cs114;

import java.util.Scanner;

public class AtoY {

    private static class Cell {
        public final int r, c; // row and column indices
        public Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    //  check if the cell indices are valid
    private static boolean valid(int rowIndex, int colIndex) {
        return (rowIndex >= 0 && rowIndex < 5 && colIndex >= 0 && colIndex < 5);
    }

    // Return the neighbor cell of (row,col) that contains "ch"  if it exists, else
    // return null
    private static Cell findNeighborCell(char [] [] t, int row, int col, char ch) {
        if (valid(row-1,col) && t[row-1][col] == ch) {
            return new Cell(row-1,col);
        }
        if (valid(row+1,col) && t[row+1][col] == ch) {
            return new Cell(row+1,col);
        }
        if (valid(row,col-1) && t[row][col-1] == ch) {
            return new Cell(row,col-1);
        }
        if (valid(row,col+1) && t[row][col+1] == ch) {
            return new Cell(row,col+1);
        }
        return null;
    }

    public static void printTable(char[][] t) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j)
                System.out.print(t[i][j]);
            System.out.println();
        }
    }
    private static boolean solve(char[][] t, int row, int col, char c) {
        /**
         * Complete code here
         */
        if(c=='y'){
            return true;
        }
        else{
            char nextCh = (char)((int)c+1);
            Cell temp = findNeighborCell(t,row, col, nextCh);
            if(temp!=null){
                return solve(t, temp.r, temp.c, nextCh);
            }
            else{
                if(valid(row-1, col) && t[row-1][col]=='z'){
                    t[row-1][col] = nextCh;
                    if(solve(t, row-1, col, nextCh)){
                        return true;
                    }
                    else{
                        t[row-1][col]='z';
                    }
                }
                if(valid(row+1, col) && t[row+1][col]=='z'){
                    t[row+1][col] = nextCh;
                    if(solve(t, row+1, col, nextCh)){
                        return true;
                    }
                    else{
                        t[row+1][col]='z';
                    }
                }
                if(valid(row, col-1) && t[row][col-1]=='z'){
                    t[row][col-1] = nextCh;
                    if(solve(t, row, col-1, nextCh)){
                        return true;
                    }
                    else{
                        t[row][col-1]='z';
                    }
                }
                if(valid(row, col+1) && t[row][col+1]=='z'){
                    t[row][col+1] = nextCh;
                    if(solve(t, row, col+1, nextCh)){
                        return true;
                    }
                    else{
                        t[row][col+1]='z';
                    }
                }
            }
        }
        
        return false;
    }


    public static void main(String[] args) {
        System.out.println("Enter 5 rows of lower-case letters a to z below. Note z indicates empty cell");
        Scanner sc = new Scanner(System.in);
        char[][] tbl = new char[5][5];
        int row = -1, col = -1;
        String inp;
        for (int i = 0; i < 5; ++i) {
            inp = sc.next();
            for (int j = 0; j < 5; ++j) {
                tbl[i][j] = inp.charAt(j);
                if (tbl[i][j] == 'a') {
                    row = i;
                    col = j;
                }
            }
        }

        if (solve(tbl, row, col, 'a')) {
            System.out.println("Printing the solution...");
            printTable(tbl);
        } else {
            System.out.println("There is no solution");
        }
    }
}
