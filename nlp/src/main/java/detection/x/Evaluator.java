package detection.x;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import utils.NlpUtils;
import utils.entity.WordOfInterest;

import com.google.gson.Gson;

import detection.entity.DetectionContext;
import detection.entity.EmotionIndexSearchResult;
import detection.entity.GenericSearchResult;
import detection.entity.QualityIndexSearchResult;

import elasticsearch.ESOperator;
import elasticsearch.entity.IndexData;

public class Evaluator {

	private ESOperator operator;
	private static Gson gson;
	private NlpUtils nlpUtils;
	
	private final static String EVALUATOR_PROPERTY_FILES = "evaluate.properties";
	private Properties evaluatorProperties;
	
	private Integer MIN_TOKEN_SIZE;
	private Integer COMBINATION_LENGTH;
	
	public Evaluator()
	{
		operator = new ESOperator();
		gson = new Gson();
		nlpUtils = new NlpUtils();
		
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
		
		MIN_TOKEN_SIZE = Integer.valueOf(evaluatorProperties.getProperty("minTokenSize"));
		COMBINATION_LENGTH = Integer.valueOf(evaluatorProperties.getProperty("combinationLength"));
	}
	
	// Not proud, short time, almost direct replica from trainer, very ugly
	public List<GenericSearchResult> searchMaker(String phrase, boolean useRealMode)
	{		
		List<WordOfInterest> allTokens = nlpUtils.tokenizeAndCleanText(phrase);
		List<WordOfInterest> customTokens = new ArrayList<WordOfInterest>();
		
		// Min size strategy
		for(WordOfInterest w : allTokens)
		{
			if(w.getWord().length() >= MIN_TOKEN_SIZE)
			{
				customTokens.add(w);
			}
		} 
		
		// NEGATITION NULLIFIER HERE IS A MUST
		
		// Maybe more preventive indexing strategy up here ?	
		
		// Combinations strategy
		int globalCombinations = 0;
		int customTokenListSize = customTokens.size();
		List<GenericSearchResult> searchResult = new ArrayList<GenericSearchResult>();
		while(globalCombinations <= COMBINATION_LENGTH)
		{
			int iterator = 0;
			while(iterator < customTokenListSize)
			{
				boolean endOfLine = false;
				int localCombinations = 0;
				List<WordOfInterest> localTokens = new ArrayList<WordOfInterest>();
				while(localCombinations <= globalCombinations)
				{
					if(iterator + localCombinations < customTokenListSize)
						localTokens.add(customTokens.get(iterator + localCombinations));
					else
					{
						endOfLine = true;
						break;
					}
					
					localCombinations++;
				}
				if(!endOfLine)
				{
					String s = "";
					List<String> searchable = new ArrayList<String>();;
					for (WordOfInterest woi :localTokens)
					{
						searchable.add(useRealMode ? woi.getWord() : woi.getConversion());
					}
					searchResult.add(searchSingleResultInEmotionIndex(searchable,useRealMode,DetectionContext.EMOTION));
				}
					
				iterator++;
			}
			globalCombinations++;
		}
		
		// Repetition elimination strategy perhaps here ?
		
		return searchResult;
	}
	
	public GenericSearchResult searchSingleResultInEmotionIndex(List<String> words, boolean realMode, DetectionContext ctx)
	{
		return searchSingleResult(realMode, ctx, words);
	}
	
	// Mapper 
	private GenericSearchResult searchSingleResult(boolean realMode, DetectionContext detectionContext, List<String> words)
	{
		String wordKey = "";
		for(String word : words)
		{
			word = word.toLowerCase();
			wordKey += "_" + word;
		}
		SearchResponse searchResponse = 
				realMode ? operator.searchByOrigin(wordKey) : operator.searchByConversion(wordKey);
			
		GenericSearchResult mappedSearchResponse = initSearchResult(detectionContext);	
		
		mappedSearchResponse.setTotalHits(searchResponse.getHits().getTotalHits());
		mappedSearchResponse.setSearchQuery(wordKey);

		for(SearchHit hit : searchResponse.getHits())
		{
			String resultJson = hit.getSourceAsString();
			
			@SuppressWarnings("unchecked")
			Map<String,Double> map = gson.fromJson(resultJson, Map.class);
			
			for(String criteria : mappedSearchResponse.getCriteria())
			{
				Double valueAtSingleIndex = map.get(criteria) == null ? 0 : map.get(criteria);
				Double valueAtResultMap = mappedSearchResponse.getResultMap().get(criteria) == null ? 0 : mappedSearchResponse.getResultMap().get(criteria);

				mappedSearchResponse.getResultMap().put(criteria, valueAtResultMap + valueAtSingleIndex);
			}
		}
		
		return mappedSearchResponse;
	}
	
	private GenericSearchResult initSearchResult(DetectionContext detectionContext)
	{
		if(DetectionContext.EMOTION.equals(detectionContext))
			return new EmotionIndexSearchResult();
		else if(DetectionContext.QUALITY.equals(detectionContext))
			return new QualityIndexSearchResult();

		System.out.println("Unsupported detection context");
		
		return null;
	}
}
