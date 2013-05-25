package detection.x;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import utils.EmotionConstants;

import detection.entity.EmotionEvaluation;
import detection.entity.GenericSearchResult;

public class EmotionMapper {

	private final static String EVALUATOR_PROPERTY_FILES = "evaluate.properties";
	private Properties evaluatorProperties;
	
	private Integer MIN_HITS;
	private Integer MAX_HITS;
	private Integer DELETE_EXCEEDING_HITS;

	private Double SECOND_QUANTIFIER;
	private Double THIRD_QUANTIFIER;
	private Double FOURTH_QUANTIFIER;
	
	private boolean USE_LEVEL_DATA_BIAS;
	
	// LOCAL QUANTIFIERS START HERE
	
	private Double JOY_QUANTIFIER;
	private Double SADNESS_QUANTIFIER;
	private Double TRUST_QUANTIFIER;
	private Double DISGUST_QUANTIFIER;
	private Double FEAR_QUANTIFIER;
	private Double ANGER_QUANTIFIER;
	private Double SUPRISE_QUANTIFIER;
	private Double ANTICIPATION_QUANTIFIER;
	
	// LOCAL QUANTIFIERS END HERE
	
	public EmotionMapper()
	{
		evaluatorProperties = new Properties();
		try {
			evaluatorProperties.load(new FileInputStream(EVALUATOR_PROPERTY_FILES));
		} catch (FileNotFoundException e) {
			System.out.println("properties not found trainerProperties");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io exception when loading trainerProperties");
			e.printStackTrace();
		}
		
		MIN_HITS = Integer.valueOf(evaluatorProperties.getProperty("minHitsRequiredForSingleResult"));
		MAX_HITS = Integer.valueOf(evaluatorProperties.getProperty("maxHitsAcceptedForSingleResult"));
		DELETE_EXCEEDING_HITS = Integer.valueOf(evaluatorProperties.getProperty("deleteOnExceedingSingleResult"));
		
		SECOND_QUANTIFIER = Double.valueOf(evaluatorProperties.getProperty("secondRelationWeightQuantifier"));
		THIRD_QUANTIFIER = Double.valueOf(evaluatorProperties.getProperty("thirdRelationWeightQuantifier"));
		FOURTH_QUANTIFIER = Double.valueOf(evaluatorProperties.getProperty("fourthRelationWeightQuantifier"));
		
		USE_LEVEL_DATA_BIAS = Boolean.valueOf(evaluatorProperties.getProperty("levelDataBiasSwitch"));
		
		JOY_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_joy);
		SADNESS_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_sadness);
		TRUST_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_trust);
		DISGUST_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_disgust);
		FEAR_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_fear);
		ANGER_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_anger);
		SUPRISE_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_suprise);
		ANTICIPATION_QUANTIFIER = Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_total_files) 
				/ Double.valueOf(EmotionConstants.INDEXING_GLOBALS.global_anticipation);
		
	}
	
	public EmotionEvaluation mapGenericSearchResultSetToEmotionEvaluation(List<GenericSearchResult> list)
	{
		EmotionEvaluation eve = new EmotionEvaluation();
		
		for(GenericSearchResult res : list)
		{
			GenericSearchResult originalForm = new GenericSearchResult(res) {
				
				@Override
				public List<String> getCriteria() {
					// TODO Auto-generated method stub
					return Arrays.asList("joy", "sadness", "trust", 
							"disgust", "fear", "anger", "suprise", "anticipation", "general");
				}
			};
			eve.getOriginalSearchResult().add(originalForm);
			GenericSearchResult processed = processSingleSearchResult(res);
			if(processed != null)
				eve.getProcessedSearchResult().add(processed);
		}
		
		arrangeEvaluationTotalValues(eve);
		
		return eve;
	}
	
	private void arrangeEvaluationTotalValues(EmotionEvaluation eve)
	{
		for(GenericSearchResult processed : eve.getProcessedSearchResult())
		{
			eve.setJoy(eve.getJoy() + processed.getResultMap().get("joy"));
			eve.setSadness(eve.getSadness() + processed.getResultMap().get("sadness"));
			eve.setTrust(eve.getTrust() + processed.getResultMap().get("trust"));
			eve.setDisgust(eve.getDisgust() + processed.getResultMap().get("disgust"));
			eve.setFear(eve.getFear() + processed.getResultMap().get("fear"));
			eve.setAnger(eve.getAnger() + processed.getResultMap().get("anger"));
			eve.setSuprise(eve.getSuprise() + processed.getResultMap().get("suprise"));
			eve.setAnticipation(eve.getAnticipation() + processed.getResultMap().get("anticipation"));
			
			eve.setTotal_hits(eve.getTotal_hits() + processed.getTotalHits());
		}
		
		
	}
	
	private GenericSearchResult processSingleSearchResult(GenericSearchResult res)
	{
		if( res.getTotalHits() < MIN_HITS)
			return null;
		
		if( res.getTotalHits() > DELETE_EXCEEDING_HITS)
			return null;
		
		Double maxHitQuantifier = 1.0;
		if( Double.valueOf(res.getTotalHits()) > MAX_HITS)
			maxHitQuantifier = MAX_HITS / Double.valueOf(res.getTotalHits());
		
		int level = searchQueryToQuantifierLevel(res.getSearchQuery());
		Double levelQuantifier = 1.0;
		if(level == 0)
			System.out.println("Index fault detected at evaluation level");
		else if (level == 2)
			levelQuantifier = SECOND_QUANTIFIER;
		else if (level == 3)
			levelQuantifier = THIRD_QUANTIFIER;
		else if (level == 4)
			levelQuantifier = FOURTH_QUANTIFIER;
		
		// Logical ?
		if(USE_LEVEL_DATA_BIAS)
		{
			res.getResultMap().put("joy", res.getResultMap().get("joy") * JOY_QUANTIFIER);
			res.getResultMap().put("sadness", res.getResultMap().get("sadness") * SADNESS_QUANTIFIER);
			res.getResultMap().put("trust", res.getResultMap().get("trust") * TRUST_QUANTIFIER);
			res.getResultMap().put("disgust", res.getResultMap().get("disgust") * DISGUST_QUANTIFIER);
			res.getResultMap().put("fear", res.getResultMap().get("fear") * FEAR_QUANTIFIER);
			res.getResultMap().put("anger", res.getResultMap().get("anger") * ANGER_QUANTIFIER);
			res.getResultMap().put("suprise", res.getResultMap().get("suprise") * SUPRISE_QUANTIFIER);
			res.getResultMap().put("anticipation", res.getResultMap().get("anticipation") * ANTICIPATION_QUANTIFIER);
		}
		
		List<String> keys = res.getCriteria();
		for(String key : keys)
		{
			res.getResultMap().put(key, res.getResultMap().get(key) * levelQuantifier * maxHitQuantifier);
		}
		
		
		return res;
	}
	
	// WOW . =)
	private int searchQueryToQuantifierLevel(String query)
	{
		int lastIndex = 0;
		int count =0;

		while(lastIndex != -1){

		       lastIndex = query.indexOf('_',lastIndex);

		       if( lastIndex != -1){
		             count ++;
		             lastIndex+=1;
		      }
		}
		return count;
	}
}
