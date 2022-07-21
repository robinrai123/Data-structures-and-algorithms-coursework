/*
By Robin Rai (100242165)
V.1.0.0
Created on 05/03/2020
*/
package dsacoursework2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Trie {
	private TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	public Trie(TrieNode input) {
		root = input;
	}

	boolean add(String key) {
		if (key.equals("")) {
			return false;
		}
		int index;
		boolean alreadyIn = true;
		TrieNode temp = root;
		for (int level = 0; level < key.length(); level++) {
			//goes through all letters of the key
			index = key.charAt(level) - 'a';
			if (temp.getOffspring()[index] == null) {
				//if the node for the current letter doesn't exist
				temp.getOffspring()[index] = new TrieNode();
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
		return true;
	}

	boolean contains(String key) {
		int level;
		int length = key.length();
		int index;
		TrieNode temp = root;
		for (level = 0; level < length; level++) {
			//goes through all letters of the key
			index = key.charAt(level) - 'a';
			//index is current char's number
			if (temp.getOffspring()[index] == null) {
				//if the node for the current letter doesn't exist
				return false;
				//return false, it isn't there
			}
			temp = temp.getOffspring()[index];
			//advance down a level
		}
		return (temp != null && temp.getIsEnd());
		//returns true only if it exists AND is a key - not just a prefix.
	}


	String outputBreadthFirstSearch() {
		Queue<TrieNode> queue = new LinkedList<>();
		StringBuilder builder = new StringBuilder();
		queue.add(root);
		//starts with root
		while (!queue.isEmpty()) {
			TrieNode temp = queue.remove();
			//takes first thing in queue
			for (int i = 0; i < 26; i++) {
				if (!(temp.getOffspring()[i] == null)) {
					//for all of it's children
					queue.add(temp.getOffspring()[i]);
					//add them to the queue so it goes through
					//their children - width first
					builder.append((char) (i + 97));
					//adds it to the output
				}
			}
		}
		return builder.toString();
	}

	public String outputDepthFirstSearch() {
		//helper function, provides string to append to
		StringBuilder builder = new StringBuilder();
		return outputDepthFirstSearch(builder, this.root);
	}

	String outputDepthFirstSearch(StringBuilder builder, TrieNode trieNode) {
		TrieNode[] children = trieNode.getOffspring();
		//makes an array of all children in the tree
		for (int i = 0; i < 26; i++) {
			//for all of a node's children
			if (children[i] != null) {
				//if there's a child
				builder.append((char) (i + 97));
				//add it's character
				outputDepthFirstSearch(builder, children[i]);
				//call the function again on it's children
			}
		}
		return builder.toString();
	}

	Trie getSubTrie(String key) {

		TrieNode temp = root;
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
		return new Trie(temp);
		//returns only if it exists AND is a key - not just a prefix.

	}

	public ArrayList<String> getAllWords() {
		//helper function, is used just for the ArrayList and String/stringBuilder
		ArrayList<String> listOfWords = new ArrayList<>();
		getAllWords(listOfWords, new StringBuilder(), root);
		return listOfWords;
	}

	private void getAllWords(ArrayList listOfWords, StringBuilder builder,
							 TrieNode root) {
		if (root.getIsEnd()) {
			//checks if it's reached the end
			//System.out.println(builder.toString());
			listOfWords.add(builder.toString());
			//print it and add it to the array
		}
		TrieNode[] offspring = root.getOffspring();
		//get's current node's children
		for (int i = 0; i < offspring.length; i++) {
			//for all the children
			if (offspring[i] != null) {    //if it's a valid node
				getAllWords(listOfWords, builder.append((char)
						(97 + i)), offspring[i]);
				//call itself, with the child as the root
				builder.setLength(builder.length() - 1);
				//goes up a level - resets stringbuilder to right place
			}
		}
	}

	public static void main(String args[]) {
		Trie potato = new Trie();
		System.out.println("Adding...");
		System.out.println(potato.add("bat"));
		System.out.println(potato.add("cat"));
		System.out.println(potato.add("chat"));
		System.out.println(potato.add("cheese"));
		System.out.println(potato.add("cheers"));

		System.out.println("Checking...");
		System.out.println(potato.contains("yeet"));
		System.out.println(potato.contains("ca"));
		System.out.println(potato.contains("cat"));

		System.out.println("Breadth...");
		System.out.println(potato.outputBreadthFirstSearch());
		System.out.println("Depth...");
		System.out.println(potato.outputDepthFirstSearch());

		System.out.println("getSubTrie \"ch\"");
		Trie potato2 = potato.getSubTrie("ch");

		System.out.println("Breadth...");
		System.out.println(potato2.outputBreadthFirstSearch());
		System.out.println("Depth...");
		System.out.println(potato2.outputDepthFirstSearch());

		System.out.println("Checking...");
		System.out.println(potato2.contains("eese"));
		System.out.println(potato2.contains("eers"));
		System.out.println(potato2.contains("at"));
		System.out.println(potato2.contains("yeet"));
		System.out.println(potato.getAllWords());
	}
}
