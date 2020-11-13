package edu.njit.cs114;

import java.io.IOException;
import java.util.*;
import java.util.Arrays;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/26/2020
 */
public class AnagramFinderListImpl extends AbstractAnagramFinder {

    public List<WordArrPair> wordArrList = new ArrayList<>();

    private class WordArrPair implements Comparable<WordArrPair> {
        public final String word;
        public final char [] charArr;


        public WordArrPair(String word) {
            this.word = word;
            charArr = word.toCharArray();
            Arrays.sort(charArr);
        }

        public boolean isAnagram(WordArrPair wordArrPair) {
            /**
             * To be completed
             * Compare charArr already sorted (use Arrays.equals())
             */

            return Arrays.equals(wordArrPair.charArr, charArr);
        }

        @Override
        public int compareTo(WordArrPair wordArrPair) {

            return this.word.compareTo(wordArrPair.word);
        }


    }


    @Override
    public void clear() {
        wordArrList.clear();
    }

    @Override
    public void addWord(String word) {
        /**
         * Create a word pair object and add it to list
         */
        wordArrList.add(new WordArrPair(word));
    }

    @Override
    public List<List<String>> getMostAnagrams() {
        Collections.sort(wordArrList);
        ArrayList<List<String>> mostAnagramsList = new ArrayList<>();
        /**
         * To be completed
         * Repeatedly do this:
         * (a)Each wordPair in the list is compared to others to identify all its anagrams;
         * (b) add the anagram words to the same group;
         *      if there are no anagrams, single word forms a group
         * (c) remove these words from wordArrList
         */

        //First go throught the wordListList
        //For every index of the wordList, we want to compare that word to all the other words in the wordList
            //If a word is an anagoram, add it to the mostAnagramsList
            //REMOVE THAT WORD
        int count = 0;
        while(!wordArrList.isEmpty()){
            WordArrPair temp = new WordArrPair(wordArrList.get(0).word);
            wordArrList.remove(0);
            mostAnagramsList.add(new ArrayList<>());
            mostAnagramsList.get(count).add(temp.word);

            for(int i = 0; i<wordArrList.size(); i++){
                System.out.println("is " + temp.word + " an anagram of " + wordArrList.get(i).word + " " + temp.isAnagram(wordArrList.get(i)));
                if(temp.isAnagram(wordArrList.get(i))){
                    mostAnagramsList.get(count).add(wordArrList.get(i).word);
                    System.out.println(i+ " " + wordArrList.get(i).word);
                    wordArrList.remove(wordArrList.get(i));
                }
            }
            count++;
        }

        ArrayList<List<String>> mostAnagramsList2 = new ArrayList<>();
        int max = mostAnagramsList.get(0).size();
        System.out.println(mostAnagramsList);
        for(List<String> x : mostAnagramsList){
            if(x.size()>=max){
                max=x.size();
                mostAnagramsList2.add(x);
            }
        }

        return mostAnagramsList2;
    }

    public static void main(String [] args) {
        AnagramFinderListImpl finder = new AnagramFinderListImpl();
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
