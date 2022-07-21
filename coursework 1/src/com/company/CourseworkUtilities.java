package com.company;

import java.util.*;

// author: j.lines@uea.ac.uk

public class CourseworkUtilities {

    public static Random rand = new Random(); // seed this if you want reproducible results

    // example usage creating a dictionary and a doc
    public static void main(String[] args) throws Exception{

        // generate the dictionary
        String[] dict = generateDictionary(10, 5);

        // printing dictionary
        System.out.println("dictionary:");
        for (int i = 0; i < dict.length; i++) {
            System.out.print(dict[i]+" ");
        }
        System.out.println();

        // generating doc by passing in the dictionary and a total number
        // of words for the doc. The doc will then be generated using
        // unseen words and some words from the dictionary. These are sampled using
        // the Random object on line 6 - seed this if you would like results to be
        // reproducible
        String[] doc = generateDocument(dict, 200);

        // printing doc
        System.out.println("\ndoc:");
        for (int i = 0; i < doc.length; i++) {
            System.out.print(doc[i]+" ");
        }
        System.out.println();
    }

    // generate a word of a given length by randomly generating letters
    public static String generateWord(int wordLength){
        StringBuilder st = new StringBuilder();
        for(int i =0; i < wordLength; i++){
            st.append((char)(rand.nextInt(26)+'a'));
        }
        return st.toString();
    }

    // generate a dictionary for a given word length and number of words to generate. Note that duplicates are not
    // allowed so this method checks before adding a new word. HOWEVER, as noted, this is a crude implementation and
    // not the most efficient. This is fine for now however and you'll learn better ways later on.
    public static String[] generateDictionary(int numWords, int wordLength) throws Exception{

        if(Math.pow(26,wordLength) < numWords){
            throw new Exception("Error: the input arguments could only result in "
                    +"26^"+wordLength+" ("+((int)(Math.pow(26,wordLength)))+ ") distinct words but the"
                    +" numWords argument is set to "+numWords);
        }

        // remember - DO NOT USE IN-BUILT JAVA DATA STRUCTURES IN YOUR OWN CODE FOR THIS ASSIGNMENT (you can still
        // use arrays wherever you like, however)
        //
        // It is fine to use ArrayList here as this has been given to you but do not use it
        // anywhere else in your coursework.

        ArrayList<String> dictionary = new ArrayList<>(numWords);
        String temp;
        while(dictionary.size() < numWords){
            temp = generateWord(wordLength);
            if(!dictionary.contains(temp)){
                dictionary.add(temp);
            }
        }
        return dictionary.toArray(new String[dictionary.size()]);
    }

    // similar to generating a dictionary but simpler - generate a given number of random words of a specified length.
    // No need to check for duplicates here - this method just fills up your doc with other words for
    // testing/timing but it doesn't matter what they are
    public static String[] generateFillerWords(int numWords, int wordLength){

        String[] output = new String[numWords];
        for(int i = 0; i < numWords; i++){
            output[i] = generateWord(wordLength);
        }
        return output;
    }

    // uses all of the above to generate a doc when passed a dictionary. Randomly samples with a uniform
    // distribution (i.e. each word is as likely to be picked as any other) so for very large docs you should
    // expect similar counts of each word
    public static String[] generateDocument(String[] dictionary, int numWordsInDoc){
        // generate other words to fill the document with
        String[] otherWords = generateFillerWords(dictionary.length*2,dictionary[0].length());

        String[] documentList = new String[numWordsInDoc];

        int nextWordIdx;
        int numDistinctWords = dictionary.length*3;

        StringBuilder st = new StringBuilder();
        for(int i = 0; i < numWordsInDoc;i++){
            nextWordIdx = rand.nextInt(numDistinctWords);
            if(nextWordIdx < dictionary.length) {
                documentList[i] = dictionary[nextWordIdx];
            }else{
                documentList[i] = otherWords[nextWordIdx-dictionary.length];
            }
        }
        return documentList;
    }
}