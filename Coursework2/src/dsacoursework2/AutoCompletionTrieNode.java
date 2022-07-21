/*
By Robin Rai (100242165)
V.1.0.0
Created on 05/03/2020
*/
package dsacoursework2;

public class AutoCompletionTrieNode {
	private AutoCompletionTrieNode[] offspring;
	//a is 0, b is 1, etc
	private boolean isEnd;
	//if the node is the end of a word/key
	private int frequency;
	//added frequency of word - is to be used at the end of a word like isEnd


	public AutoCompletionTrieNode() {
		isEnd = false;
		this.offspring = new AutoCompletionTrieNode[26];
		frequency = 0;
	}

	public AutoCompletionTrieNode[] getOffspring() {
		return this.offspring;
	}

	public boolean getIsEnd() {
		return this.isEnd;
	}

	public void setIsEnd(boolean input) {
		this.isEnd = input;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int input) {
		this.frequency = input;
	}
}
