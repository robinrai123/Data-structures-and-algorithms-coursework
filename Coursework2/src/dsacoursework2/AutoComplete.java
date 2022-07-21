/*
By Robin Rai (100242165)
V.1.0.0
Created on 05/03/2020
*/
package dsacoursework2;

import java.io.IOException;
import java.util.ArrayList;

import static dsacoursework2.DictionaryMaker.*;
import static dsacoursework2.AutoCompletionTrie.*;

public class AutoComplete {

	public static void autoCompletion(AutoCompletionTrie masterTrie, ArrayList<String> prefixes, String saveLocation) throws IOException {
		//generates top three results for each query, prints them to console and file
		String infoString = "";
		//string to be written to file
		for (int prefix = 0; prefix < prefixes.size(); prefix++) {
			//for every prefix
			System.out.println("\nResults for: " + prefixes.get(prefix));
			AutoCompletionTrie currentSubTrie = masterTrie.getSubTrie(prefixes.get(prefix));
			//gets the subTrie for the prefix
			ArrayList<fullInfo> info = currentSubTrie.getAllInfo();
			//gets the information for the subTrie
			int totalFreq = 0;
			//counter
			for (int i = 0; i < info.size(); i++) {
				totalFreq += info.get(i).getFreq();
				//sums up frequencies to calculate ratios
			}
			infoString += prefixes.get(prefix) + ",";
			//starts string to write to file
			for (int i = 0; i < 3 && i < info.size(); i++) {
				//gets top three or all available sub words, whichever's smallest
				infoString += prefixes.get(prefix) + info.get(i).getKey() + "," + info.get(i).getFreq() + "," + (double) info.get(i).getFreq() / totalFreq + ",";
				//creates a line with all words under that prefix along with their info to write to file
				System.out.println(prefixes.get(prefix) + info.get(i).getKey() + " (probability " + (double) info.get(i).getFreq() / totalFreq + ")");
				//prints said info to console as well
			}
			infoString = infoString.substring(0, infoString.length() - 1);
			//removes comma from last result, unnecessary.
			infoString += "\n";
			//new line for new prefix
		}
		saveToFile(infoString, saveLocation);
		//calls saveToFile from DictionaryMaker to save to file
	}

	public static void main(String[] args) throws IOException {
		dsacoursework2.DictionaryMaker df = new dsacoursework2.DictionaryMaker();
		df.formDictionary("src\\TextFiles\\lotr.csv", "src\\TextFiles\\lotrDic.csv");
		AutoCompletionTrie completionTest = new AutoCompletionTrie();
		completionTest.addToTrie("src\\TextFiles\\lotrDic.csv");
		autoCompletion(completionTest, readWordsFromCSV("src\\TextFiles\\lotrQueries.csv", "\n"),
				"src\\TextFiles\\lotrMatches.csv");

	}

}
