package training.entity;

import java.util.ArrayList;
import java.util.List;

import elasticsearch.entity.Weight;

public class Trainable {

	private String phrase;
	private List<Weight> weight;
	
	public Trainable()
	{
		weight = new ArrayList<Weight>();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type;

	public List<Weight> getWeight() {
		return weight;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	
}
