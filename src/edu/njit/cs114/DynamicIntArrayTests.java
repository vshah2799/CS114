package edu.njit.cs114;

import edu.njit.cs114.DynamicIntArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/23/20
 */
public class DynamicIntArrayTests extends UnitTests {

       @Test
       public void addTest() {
           try {
               DynamicIntArray arr = new DynamicIntArray();
               arr.add(10);
               assertEquals(arr.size(),1);
               assertEquals(arr.nCopies(), 0);
               totalScore += 2;
               arr.add(45);
               assertEquals(arr.size(),2);
               assertEquals(arr.nCopies(), 1);
               totalScore += 2;
               arr.add(-5);
               arr.add(10);
               arr.add(13);
               assertEquals(arr.nCopies(), 7);
               assertEquals(arr.toString(),"(8)[10,45,-5,10,13]");
               totalScore += 4;
               success("addTest");
           } catch (Exception e) {
               failure("addTest", e);
           }
       }

       @Test
       public void addIndexTest() {
           try {
               DynamicIntArray arr = new DynamicIntArray(4);
               arr.add(40);
               arr.add(0, 35);
               assertEquals(arr.size(),2);
               assertEquals(arr.nCopies(), 0);
               totalScore += 3;
               arr.add(0, 15);
               arr.add(1, 25);
               arr.add(2, 28);
               arr.add(3, 30);
               arr.add(1, 20);
               arr.add(5, 33);
               arr.add(5, 32);
               assertEquals(arr.size(),9);
               assertEquals(arr.nCopies(), 12);
               assertEquals(arr.toString(),"(16)[15,20,25,28,30,32,33,35,40]");
               totalScore += 6;
               success("addIndexTest");
           } catch (Exception e) {
               failure("addIndexTest", e);
           }
       }

       @Test
       public void setTest() {
           try {
               DynamicIntArray arr = new DynamicIntArray(20);
               arr.add(40);
               arr.add(50);
               arr.add(25);
               assertEquals(arr.set(1,34), 50);
               assertEquals(arr.size(),3);
               totalScore += 4;
               assertEquals(arr.toString(),"(20)[40,34,25]");
               totalScore += 2;
               assertEquals(arr.set(1,-56), 34);
               assertEquals(arr.size(),3);
               totalScore += 4;
               assertEquals(arr.toString(),"(20)[40,-56,25]");
               totalScore += 2;
               success("setTest");
           } catch (Exception e) {
               failure("setTest", e);
           }
       }

       //@Test
       public void getTest() {
           try {
               DynamicIntArray arr = new DynamicIntArray(20);
               arr.add(40);
               assertEquals(arr.get(0), 40);
               assertEquals(arr.size(), 1);
               totalScore += 2;
               arr.add(50);
               arr.add(25);
               arr.add(13);
               arr.add(1,33);
               assertEquals(arr.get(1), 33);
               assertEquals(arr.get(2), 50);
               assertEquals(arr.size(),5);
               totalScore += 4;
               success("getTest");
           } catch (Exception e) {
               failure("getTest", e);
           }
       }

       @Test
       public void removeTest() {
           try {
               DynamicIntArray arr = new DynamicIntArray(20);
               arr.add(40);
               assertEquals(arr.remove(), 40);
               assertEquals(arr.size(), 0);
               totalScore += 4;
               assertEquals(arr.toString(), "(10)[]");
               totalScore += 4;
               arr.add(50);
               arr.add(25);
               arr.add(1,33);
               arr.add(13);
               assertEquals(arr.remove(), 13);
               assertEquals(arr.size(), 3);
               totalScore += 3;
               assertEquals(arr.toString(), "(10)[50,33,25]");
               totalScore += 5;
               success("removeTest");
           } catch (Exception e) {
               failure("removeTest", e);
           }
       }

    @Test
    public void removeIndexTest() {
        try {
            DynamicIntArray arr = new DynamicIntArray(20);
            arr.add(40);
            assertEquals(arr.remove(0), 40);
            assertEquals(arr.size(), 0);
            totalScore += 4;
            assertEquals(arr.toString(), "(10)[]");
            totalScore += 5;
            arr.add(50);
            arr.add(25);
            arr.add(1,33);
            arr.add(13);
            assertEquals(arr.remove(1), 33); // 25,13
            assertEquals(arr.size(), 3);
            totalScore += 3;
            assertEquals(arr.remove(0), 50);
            assertEquals(arr.size(), 2);
            totalScore += 3;
            assertEquals(arr.toString(), "(5)[25,13]");
            totalScore += 5;
            success("removeIndexTest");
        } catch (Exception e) {
            failure("removeIndexTest", e);
        }
    }

    @Test
    public void removeLargeTest() {
         try {
             int[] nItemsArr = new int[]{0, 10000, 20000, 40000};
             DynamicIntArray arr = new DynamicIntArray();
             for (int k=1; k < nItemsArr.length; k++) {
                 for (int i = 0; i < nItemsArr[k] - nItemsArr[k - 1]; i++) {
                     arr.add(i + 1);
                 }
             }
             int nCopiesForInsert = arr.nCopies();
             int size = arr.size();
             int nCopiesArr [] = {0, 32767, 16384, 0};
             for (int k = nItemsArr.length-1; k > 0; k--) {
                 for (int i = 0; i < nItemsArr[k] - nItemsArr[k - 1]; i++) {
                     arr.remove();
                 }
                 assertEquals(arr.size(), nItemsArr[k-1]);
                 assertEquals(arr.nCopies()-nCopiesForInsert, nCopiesArr[k]);
             }
             totalScore += 10;
             success("removeLargeTest");
         } catch (Exception e) {
             failure("removeLargeTest", e);
         }
    }


}
