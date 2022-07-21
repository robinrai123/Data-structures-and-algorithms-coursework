//Robin Rai
//03/12/2019
//version 6.9

package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        //cFVSquare(1000, 1000, 150, 1, 10);
        //cFVConstantDic(1000, 10000, 150, 150, 1, 10);
        //arrayTest();
        //fNDTDocListLength(350,10,1,150,150,150,500);
        //String[] doc = {"potato","carrot", "carrot"};
        // String[][] docs = {{"potato", "carrot", "carrot", "onion"}, {"potato","hi", "hi", "ilikejason"}};
        //String[] dic = {"potato", "carrot", "broccoli", "onion"};
        //System.out.println(Arrays.toString(findNearestDocuments(docs, dic)));
        //System.out.println(Arrays.toString(calculateFeatureVector(dic, doc)));
    }

    static void cFVManual(int dicLength, int docLength, int wordLength, int testAmount) {
        //manual tester. Manually give the function all values, and it will average it by testAmount.
        long time = 0;
        for (int j = 1; j <= testAmount; j++) { //repeats testAmount of times
            time = time + calculateFeatureVectorTester(dicLength, docLength, wordLength);
            //gets total time
        }
        time = time / testAmount;
        //averages
        System.out.println(time);
    }

    static void cFVSquare(int totalRuns, int runsPerValue, int wordLength, int startValue, int increment) {
        //increases doc and dic length automatically and equally.
        for (int i = startValue; i < totalRuns; i = i + increment) {
            //number of values to test.
            long time = 0;
            for (int j = 1; j <= runsPerValue; j++) {   //repeats testAmount of times for each value.
                time = time + calculateFeatureVectorTester(i, i, wordLength);
                //gets total time
            }
            time = time / runsPerValue; //averages time
            System.out.println(time);
        }
    }

    static void cFVConstantDic(int totalRuns, int runsPerValue, int dicLength, int wordLength, int startValue,
                               int increment) {
        //increases doc length automatically and manually requires everything else
        for (int i = startValue; i <= totalRuns; i = i + increment) {
            //number of values to test
            long time = 0;
            for (int j = 1; j <= runsPerValue; j++) {   //repeats testAmount of times for each value.
                time = time + calculateFeatureVectorTester(dicLength, i, wordLength);
                //gets total time
            }
            time = time / runsPerValue;     //averages time
            System.out.println(time);
        }
    }


    static long calculateFeatureVectorTester(int dicLength, int docLength, int wordLength) {
        //test funtion that actually uses the function
        long startTime = 0;
        long endTime = 0;
        try {
            String[] dic = CourseworkUtilities.generateDictionary(dicLength, wordLength);
            String[] doc = CourseworkUtilities.generateDocument(dic, docLength);
            //generates dictionary and document
            startTime = System.nanoTime();
            calculateFeatureVector(dic, doc);
            //times only the function and nothing else
            endTime = System.nanoTime();
        } catch (Exception e) {
            System.out.println("ya done m ucked up" + e);
        }
        long timeTaken = endTime - startTime;
        //calculates time taken and returns
        return timeTaken;
    }

    static int[] calculateFeatureVector(String[] dictionary, String[] document) {
        int[] outputArray = new int[dictionary.length];
        //makes new output array, the same size of the dictionary
        for (int i = 0; i < dictionary.length; i++) {
            //for every item in dictionary
            int counter = 0;    //reset counter
            for (int j = 0; j < document.length; j++) {
                //nested loop, for every item in document
                if (document[j].equals(dictionary[i])) {
                    //if current thing in doc is in current item of dictionary
                    counter++;
                    //increment counter
                }
            }
            outputArray[i] = counter;   //dictionary's word was in document counter number of times
        }
        return outputArray; //return array
    }


    static void fNDTDocLength(int docListLength, int increment, int startValue, int dicLength, int docLength,
                              int wordLength, int runsPerValue) {
        //increases doc length automatically and requires manual values for everything else
        for (int i = startValue; i <= docLength; i += increment) {
            //for each value of doc
            long time = 0;
            for (int j = 0; j < runsPerValue; j++) {
                //do each value multiple times
                time = time + findNearestDocumentsTester(dicLength, i, wordLength, docListLength);
                //add total time
            }
            time = time / runsPerValue;
            //calculate average time
            System.out.println(time);
        }
    }

    static void fNDTDicLength(int docListLength, int increment, int startValue, int dicLength, int docLength,
                              int wordLength, int runsPerValue) {
        //increases dic length automatically and requires manual values for everything else
        for (int i = startValue; i <= dicLength; i += increment) {
            //for each value of dic
            long time = 0;
            for (int j = 0; j < runsPerValue; j++) {
                //do each value multiple times
                time = time + findNearestDocumentsTester(i, docLength, wordLength, docListLength);
                //increment total time
            }
            time = time / runsPerValue;
            //average time
            System.out.println(time);
        }
    }

    static void fNDTDocListLength(int maxDocListLength, int increment, int startValue, int dicLength, int docLength,
                                  int wordLength, int runsPerValue) {
        //increases the length of docList automatically, requiring manual values for everything else
        for (int i = startValue; i <= maxDocListLength; i += increment) {
            // for every value of docList
            long time = 0;
            for (int j = 0; j <= runsPerValue; j++) {
                //do each value multiple times
                time = time + findNearestDocumentsTester(dicLength, docLength, wordLength, i);
                //increment total time
            }
            time = time / runsPerValue;
            //average time
            System.out.println(time);
        }
    }

    static long findNearestDocumentsTester(int dicLength, int docLength, int wordLength, int docListLength) {
        //generates a dictionary and a document array full of documents and runs findNearestDocuments
        long startTime = 0;
        long endTime = 0;
        try {
            String[] dic = CourseworkUtilities.generateDictionary(dicLength, wordLength);
            String[][] docArray = new String[docListLength][];  //creates docArray for the function test
            for (int i = 0; i < docListLength; i++) {   //fills docArray with documents generated from dictionary.
                docArray[i] = CourseworkUtilities.generateDocument(dic, docLength);
            }
            startTime = System.nanoTime();  //timing only the function call, and nothing else.
            findNearestDocuments(docArray, dic);    //runs function
            endTime = System.nanoTime();    //timing only the function call
        } catch (Exception e) {
            System.out.println("bad things have happened");
        }
        long timeTaken = endTime - startTime;   //calculates time taken
        return timeTaken;   // returns time taken
    }


    static int dsd(int[] feature1, int[] feature2) {
        int counter = 0;
        int n = feature1.length;
        for (int i = 0; i < n; i++) {   //for every item in the first feature vector
            if (feature1[i] >= feature2[i]) {
                counter = counter + (feature1[i] - feature2[i]);    //increments counter by distance of elements
            } else {
                counter = counter + (feature2[i] - feature1[i]);    //increments counter by distance of elements
            }
        }
        return counter;     //returns total distance for the two features.


    }

    static int[] findNearestDocuments(String[][] documentArray, String[] dictionary) {
        int n = documentArray.length;   //for loop is for every element in the docArray
        int minimumIndex = 0;   //index of the smallest value
        int[] outputArray = new int[n];
        int[][] featureArray = new int[n][];    //where the feature vectors of the documents go

        for (int i = 0; i < n; i++) {   //loop calculates all the feature vectors for the documents in documentArray
            featureArray[i] = calculateFeatureVector(documentArray[i], dictionary);
        }
        for (int i = 0; i < n; i++) {   //go through every item
            int minimum = 2147483647;      //resets minimum to largest value
            for (int j = 0; j < n; j++) {   //nested loop - for every item again

                if (i == j) {   //if comparing document with itself, ignore
                    continue;
                } else {
                    int temp = dsd(featureArray[i], featureArray[j]);   //finds distance between documents
                    if (temp < minimum) {   //if it's the newest smallest distance
                        minimum = temp;     //set minimum to this distance
                        minimumIndex = j;   //record the index/document of the new smallest distance
                    }


                }
            }
            outputArray[i] = minimumIndex;      //the closest document to i is minimumIndex/j
        }
        return outputArray;
    }
}
