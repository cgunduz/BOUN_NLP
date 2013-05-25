package training;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.elasticsearch.cluster.metadata.MappingMetaData.Id;

import elasticsearch.ESOperator;
import elasticsearch.entity.IndexData;
import elasticsearch.entity.Weight;

import training.entity.Trainable;
import utils.NlpUtils;
import utils.entity.WordOfInterest;

public abstract class Trainer {

	private NlpUtils nlpUtils;
	private final static String TRAINER_PROPERTY_FILES = "training.properties";
	private Properties trainerProperties;
	private ESOperator esOperator;
	
	private static Integer MIN_TOKEN_SIZE;
	private static Integer COMBINATION_LENGTH;
	private static String TRAINING_TYPE;
	
	public Trainer()
	{
		esOperator = new ESOperator();
		nlpUtils = new NlpUtils();
		trainerProperties = new Properties();
		try {
			trainerProperties.load(new FileInputStream(TRAINER_PROPERTY_FILES));
		} catch (FileNotFoundException e) {
			System.out.println("properties not found trainerProperties");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io exception when loading trainerProperties");
			e.printStackTrace();
		}
		
		MIN_TOKEN_SIZE = Integer.valueOf(trainerProperties.getProperty("minTokenSize"));
		COMBINATION_LENGTH = Integer.valueOf(trainerProperties.getProperty("combinationLength"));
		TRAINING_TYPE = trainerProperties.getProperty("trainingType");
	}
	
	public Properties getTrainerProperties() {
		return trainerProperties;
	}
	
	public void train(List<Trainable> tList)
	{
		for(Trainable t : tList)
			train(t);
	}
	
	public void train(Trainable t)
	{
		List<IndexData> indexDataList = trainableToIndexData(t);
		for(IndexData id : indexDataList)
			esOperator.index(id);
		
	}
	
	public abstract List<Trainable> createTrainablesFromFile(String filepath, String filename);
	
	private List<IndexData> trainableToIndexData(Trainable t)
	{
		String phrase = t.getPhrase();
		
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
		List<IndexData> indexData = new ArrayList<IndexData>();
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
					indexData.add(new IndexData(TRAINING_TYPE, localTokens, t.getWeight()));
				iterator++;
			}
			globalCombinations++;
		}
		
		// Repetition elimination strategy perhaps here ?
		
		return indexData;
	}
	
}
