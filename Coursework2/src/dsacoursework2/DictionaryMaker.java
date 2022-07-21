/*
By/Modified by Robin Rai (100242165)
V.1.0.0
Created on 05/03/2020
*/
package dsacoursework2;

import java.io.*;
import java.util.*;

public class DictionaryMaker {

	public DictionaryMaker() {
	}

	public static ArrayList<String> readWordsFromCSV(String file, String delim)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(file));
		sc.useDelimiter(delim);
		ArrayList<String> words = new ArrayList<>();
		String str;
		while (sc.hasNext()) {
			str = sc.next();
			str = str.trim();
			str = str.toLowerCase();
			words.add(str);
		}
		return words;
	}

	public static void saveToFile(Map<String, Integer> map, String file)
			throws IOException {
		//goes through any map and writes it to file
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			//System.out.println(entry.getKey() + "," + entry.getValue());
			printWriter.println(entry.getKey() + "," + entry.getValue());
			//writes key/word, followed by its value/frequency
		}
		printWriter.close();
	}

	public static void saveToFile(String line, String file) throws IOException {
		//simply writes the string to file
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(line);
		printWriter.close();
	}

	//form a set of words that exist and count the frequency of each word
	public void formDictionary(String fileDirectory, String saveDirectory) {
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		//a treeMap is the most efficient for this. The string is the key,
		// and the int is the value, since
		//all string's are unique
		try {
			ArrayList<String> temp = readWordsFromCSV(fileDirectory, ",");
			//takes in a big ol array of words
			for (int i = 0; i < temp.size(); i++) {
				map.put(temp.get(i), Collections.frequency(temp, temp.get(i)));
				//for every word, put them in the treeMap as the key, with the
				// frequency as the value
			}
		} catch (Exception e) {
			System.out.println("formDictionary: readWordsFromCSV");
		}
		//System.out.println(map);
		try {
			saveToFile(map, saveDirectory);
			//save it to file
		} catch (Exception e) {
			System.out.println("formDictionary: saveToFile");
		}
	}


	public static void main(String[] args) throws Exception {
		dsacoursework2.DictionaryMaker df = new dsacoursework2.DictionaryMaker();
		ArrayList<String> in = readWordsFromCSV(
				"src\\TextFiles\\testDocument.csv", ",");
		System.out.println("Array: " + in);

		df.formDictionary("src\\TextFiles\\testDocument.csv",
				"src\\TextFiles\\formDictionaryTest.csv");
		saveToFile("Text to write",
				"src\\TextFiles\\formDictionaryTest2.csv");
		System.out.println("Saved to file (both methods) successfully.");
	}

}
