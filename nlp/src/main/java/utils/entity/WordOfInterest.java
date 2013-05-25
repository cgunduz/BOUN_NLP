package utils.entity;

import edu.smu.tspell.wordnet.Synset;

public class WordOfInterest {

	private String word;
	private Synset synset;
	private String conversion;
	
	public WordOfInterest(String word, Synset synset, String conversion) {
		super();
		this.word = word;
		this.synset = synset;
		this.conversion = conversion;
	}

	public String getConversion() {
		return conversion;
	}

	public void setConversion(String conversion) {
		this.conversion = conversion;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Synset getSynset() {
		return synset;
	}

	public void setSynset(Synset synset) {
		this.synset = synset;
	}
	
	
}
