package elasticsearch.entity;

import java.util.List;

import utils.entity.WordOfInterest;

public class IndexData {

	private String type;
	private List<WordOfInterest> tokens;
	private List<Weight> weights;
	
	public IndexData(String type, List<WordOfInterest> tokens,
			List<Weight> weights) {
		super();
		this.type = type;
		this.tokens = tokens;
		this.weights = weights;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<WordOfInterest> getTokens() {
		return tokens;
	}
	public void setTokens(List<WordOfInterest> tokens) {
		this.tokens = tokens;
	}
	public List<Weight> getWeights() {
		return weights;
	}
	public void setWeights(List<Weight> weights) {
		this.weights = weights;
	}

	
}
