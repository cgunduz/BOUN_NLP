package detection.entity;

import java.util.Arrays;
import java.util.List;


public class EmotionIndexSearchResult extends GenericSearchResult {
	
	private static final List<String> criteriaList =
	        Arrays.asList("joy", "sadness", "trust", "disgust", "fear", "anger", "suprise", "anticipation", "general");
	
	public EmotionIndexSearchResult() {
		super();
	}
	
	@Override
	public List<String> getCriteria() {
		
		return criteriaList;
	}
	
	
}
