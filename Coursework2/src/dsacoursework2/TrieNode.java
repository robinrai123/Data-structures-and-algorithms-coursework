/*
By Robin Rai (100242165)
V.1.0.0
Created on 05/03/2020
*/
package dsacoursework2;

public class TrieNode {
	private TrieNode[] offspring;    //a is 0, b is 1, etc
	private boolean isEnd;    //if the node is the end of a word/key

	public TrieNode() {
		isEnd = false;
		this.offspring = new TrieNode[26];
		//I don't think we're getting any extra letters soon
	}

	public TrieNode[] getOffspring() {
		return this.offspring;
	}

	public boolean getIsEnd() {
		return this.isEnd;
	}

	public void setIsEnd(boolean input) {
		this.isEnd = input;
	}

}
