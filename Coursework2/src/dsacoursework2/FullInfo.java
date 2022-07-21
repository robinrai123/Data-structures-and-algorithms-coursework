package dsacoursework2;

public class FullInfo {

	String key;
	int freq;


	FullInfo(String key, int freq) {
		this.key = key;
		this.freq = freq;
	}

	FullInfo(String key, String freq) {
		this.key = key;
		this.freq = Integer.parseInt(freq);
	}
}
