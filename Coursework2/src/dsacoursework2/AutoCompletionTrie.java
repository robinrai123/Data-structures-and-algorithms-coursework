/*
By Robin Rai (100242165)
V.1.0.0
Created on 05/03/2020
*/
package dsacoursework2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AutoCompletionTrie {
	private AutoCompletionTrieNode root;

	public AutoCompletionTrie() {
		root = new AutoCompletionTrieNode();
	}

	public AutoCompletionTrie(AutoCompletionTrieNode input) {
		root = input;
	}

	boolean add(String key, int frequency) {
		if (key.equals("")) {
			return false;
		}
		int index;
		boolean alreadyIn = true;
		AutoCompletionTrieNode temp = root;
		for (int level = 0; level < key.length(); level++) {
			//goes through all letters of the key
			index = key.charAt(level) - 'a';
			if (temp.getOffspring()[index] == null) {
				//if the node for the current letter doesn't exist
				temp.getOffspring()[index] = new AutoCompletionTrieNode();
				//make a new one
				alreadyIn = false;
				//set the flag
			}
			temp = temp.getOffspring()[index];
			//advanced temp node to one further down
		}
		if (alreadyIn && temp.getIsEnd()) {
			//if everything was already done, return false
			return false;
		}
		temp.setIsEnd(true);
		//sets last node for key as end.
		temp.setFrequency(frequency);
		return true;
	}

	boolean contains(String key) {
		int level;
		int length = key.length();
		int index;
		AutoCompletionTrieNode temp = root;
		for (level = 0; level < length; level++) {
			//goes through all letters of the key
			index = key.charAt(level) - 'a';
			//index is current char's number
			if (temp.getOffspring()[index] == null) {
				//if the node for the current letter doesn't exist
				return false;
			}
			temp = temp.getOffspring()[index];
			//advance down a level
		}
		return (temp != null && temp.getIsEnd());
		//returns true only if it exists AND is a key - not just a prefix.
	}

	String outputBreadthFirstSearch() {
		if (root == null) {
			return null;
		}
		Queue<AutoCompletionTrieNode> queue = new LinkedList<>();
		StringBuilder builder = new StringBuilder();
		queue.add(root);
		//starts with root
		while (!queue.isEmpty()) {
			AutoCompletionTrieNode temp = queue.remove();
			//takes first thing in queue
			for (int i = 0; i < 26; i++) {
				if (!(temp.getOffspring()[i] == null)) {
					//for all of it's children
					queue.add(temp.getOffspring()[i]);
					//add them to the queue so it goes through their
					// children - width first
					builder.append((char) (i + 97));
					//adds it to the output
				}
			}
		}
		return builder.toString();
	}

	String outputDepthFirstSearch() {
		//helper function, provides string to append to
		StringBuilder builder = new StringBuilder();
		return outputDepthFirstSearch(builder, root);
	}

	private String outputDepthFirstSearch(StringBuilder builder,
										  AutoCompletionTrieNode trieNode) {
		AutoCompletionTrieNode[] children = trieNode.getOffspring();
		//makes an array of all children in the tree
		for (int i = 0; i < 26; i++) {
			//for all of a node's children
			if (children[i] != null) {
				//if there's a child
				builder.append((char) (i + 97));
				//add its character
				outputDepthFirstSearch(builder, children[i]);
				//call the function again on it's children
			}
		}
		return builder.toString();
	}

	AutoCompletionTrie getSubTrie(String key) {

		AutoCompletionTrieNode temp = root;
		for (int level = 0; level < key.length(); level++) {
			//for the key's length
			int index = key.charAt(level) - 'a';
			//goes to right character/node
			if (temp.getOffspring()[index] == null) {
				//checks if whole key is present
				return null;
			}
			temp = temp.getOffspring()[index];
		}
		return new AutoCompletionTrie(temp);
		//returns only if it exists AND is a key - not just a prefix.
	}

	public ArrayList<String> getAllWords() {
		//helper function, is used just for the ArrayList
		// and String/stringBuilder
		ArrayList<String> listOfWords = new ArrayList<>();
		getAllWords(listOfWords, new StringBuilder(), root);
		return listOfWords;
	}

	private void getAllWords(ArrayList listOfWords, StringBuilder sb,
							 AutoCompletionTrieNode root) {
		if (root.getIsEnd()) {
			//checks if it's reached the end
			System.out.println(sb.toString());
			listOfWords.add(sb.toString());
			System.out.println(root.getFrequency());
			//print it and add it to the array
		}
		AutoCompletionTrieNode[] children = root.getOffspring();
		//get's current node's children
		for (int i = 0; i < children.length; i++) {
			//for all the children
			if (children[i] != null) {    //if it's a valid node
				getAllWords(listOfWords, sb.append((char)
						(97 + i)), children[i]);
				//call itself, with the child as the root
				sb.setLength(sb.length() - 1);
				//goes up a level - resets stringbuilder to right place
			}
		}
	}

	public static class fullInfo {
		//objects to store a word's full info - the word
		// as well as it's frequency. Done for the comparator.
		private String key;
		private int freq;

		fullInfo(String key, int freq) {
			this.key = key;
			this.freq = freq;
		}

		fullInfo(String key, String freq) {
			this.key = key;
			this.freq = Integer.parseInt(freq);
		}

		int getFreq() {
			return freq;
		}

		String getKey() {
			return key;
		}
	}

	static class testComp implements Comparator<fullInfo> {
		public int compare(fullInfo m1, fullInfo m2) {
			//compares by frequency, then by string
			if (m1.getFreq() < m2.getFreq()) {
				return 1;
			} else if (m1.getFreq() > m2.getFreq()) {
				return -1;
			} else {
				return m1.getKey().compareTo(m2.getKey());
			}
		}
	}

	public ArrayList<fullInfo> getAllInfo() {
		//helper function to provide recursive method with string and array
		ArrayList<fullInfo> listOfInfo = new ArrayList<>();
		getAllInfo(listOfInfo, new StringBuilder(), root);
		listOfInfo.sort(new testComp());
		return listOfInfo;
	}

	private void getAllInfo(ArrayList listOfInfo, StringBuilder sb,
							AutoCompletionTrieNode root) {
		if (root.getIsEnd()) {
			listOfInfo.add(new fullInfo(sb.toString(), root.getFrequency()));
			//if the end of the word's been reached, add it to the list
		}
		AutoCompletionTrieNode[] children = root.getOffspring();
		//get's current node's children
		for (int i = 0; i < children.length; i++) {
			//for all the children
			if (children[i] != null) {
				//if it's a valid node
				getAllInfo(listOfInfo, sb.append((char)
						(97 + i)), children[i]);
				//call itself, with the child as the root
				sb.setLength(sb.length() - 1);
				//goes up a level - resets stringbuilder to right place
			}
		}
	}

	public void addToTrie(String file) throws FileNotFoundException {
		//adds a dictionary file to a trie using trie's add function
		Scanner sc = new Scanner(new File(file));
		sc.useDelimiter("\n");
		String str;
		while (sc.hasNext()) {
			str = sc.next();
			str = str.trim();
			str = str.toLowerCase();
			//reads in and trims line
			String[] parts = str.split(",");
			//splits word and frequency, and adds them to the trie
			//System.out.println("adding: " + parts[0] + " " + parts[1]);
			//System.out.println(add(parts[0], Integer.parseInt(parts[1])));
			add(parts[0], Integer.parseInt(parts[1]));
		}
	}
}
