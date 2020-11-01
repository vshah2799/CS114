package edu.njit.cs114;

import java.io.IOException;
import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 3/22/20
 */
public class AnagramFinderHashTableImpl extends AbstractAnagramFinder {

    private static final int [] primes = new int [] {2 , 3 , 5 , 7 , 11 , 13 , 17 , 19 ,
                                              23 , 29 , 31 , 37 , 41 , 43 , 47 ,
                                              53 , 59 , 61 , 67 , 71 , 73 , 79 ,
                                               83 , 89 , 97 , 101};

    private Map<Character,Integer> letterMap = new HashMap<>();

    /**
     * To be completed: Initialize anagramTable
     */
    public Map<Long, ArrayList<String>> anagramTable = new HashMap<>();


    private void buildLetterMap() {
        /**
         * To be completed
         * Assign each lower case letter to a prime number from
         * primes array, starting from 'a'. Use a for-loop to do this. Do not use
         *  26 assignment statements.
         */
        int count = 97;
        for ( Integer primeCount: primes ) {
            letterMap.put((char) count, primeCount);
            count++;
        }


    }

    public AnagramFinderHashTableImpl() {
        buildLetterMap();
    }

    /**
     * Finds hash code for a word using prime number factors
     * @param word
     * @return
     */
    public Long myHashCode(String word) {
        /**
         * To be completed
         * Use the product of powers of primes that characters of word are mapped to.
         * It should be the same for all anagrams of a word
         */
        long finalNum = 1;
        for(int i = 0; i<word.length();i++){
            char tempChar = word.charAt(i);
            finalNum = finalNum*letterMap.get(tempChar);
        }

        return finalNum;
    }

    /**
     * Add the word to the anagram table using hash code
     * @param word
     */
    @Override
    public void addWord(String word) {
        /**
         * To be completed
         * Use myHashCode to add word to the anagram table
         */
         long hash = myHashCode(word);
         if(!anagramTable.containsKey(hash)){
             anagramTable.put(hash, new ArrayList<>());
         }
         anagramTable.get(hash).add(word);
    }

    @Override
    public void clear() {
        anagramTable.clear();
    }


    /**
     * Return list of groups of anagram words which have most anagrams
     * @return
     * @throws Exception
     */
    @Override
    public List<List<String>> getMostAnagrams() {
        ArrayList<List<String>> mostAnagramsList = new ArrayList<>();
        /**
         * To be completed
         */

        int max = 0;
        for(ArrayList temp :anagramTable.values()){
            if(temp.size()>max){
                max = temp.size();
            }
        }
        for(ArrayList temp :anagramTable.values()){
            if(temp.size()==max){
                mostAnagramsList.add(temp);
            }
        }

        return mostAnagramsList;
    }

    public static void main(String [] args) {
        AnagramFinderHashTableImpl finder = new AnagramFinderHashTableImpl();
        finder.clear();
        long startTime = System.nanoTime();
        int nWords=0;
        try {
            nWords = finder.processDictionary("words.txt");
        } catch (IOException e) {
            e.printStackTrace ();
        }
        List<List<String>> anagramGroups = finder.getMostAnagrams();
        long estimatedTime = System.nanoTime () - startTime ;
        double seconds = ((double) estimatedTime /1000000000) ;
        System.out.println("Number of words : " + nWords);
        System.out.println("Number of groups of words with maximum anagrams : "
                + anagramGroups.size());
        if (!anagramGroups.isEmpty()) {
            System.out.println("Length of list of max anagrams : " + anagramGroups.get(0).size());
            for (List<String> anagramGroup : anagramGroups) {
                System.out.println("Anagram Group : "+ anagramGroup);
            }
        }
        System.out.println ("Time (seconds): " + seconds);

    }


}
