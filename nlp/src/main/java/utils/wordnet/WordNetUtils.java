package utils.wordnet;

import java.util.ArrayList;
import java.util.List;

import utils.entity.WordOfInterest;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordNetUtils {
	
	WordNetDatabase database;
	Synset dummySynset;
	
	public WordNetUtils(){
		if(System.getProperty("wordnet.database.dir") == null)
			System.setProperty("wordnet.database.dir", "/home/cmgndz/Developer/workspaces" +
					"/eclipse-workspace/nlp/WordNet-3.0/dict");
		database = WordNetDatabase.getFileInstance();
		dummySynset = database.getSynsets("DUMMY")[0];
	}
	
	public WordNetUtils(String wordNetPath) {
		
		if(System.getProperty("wordnet.database.dir") == null)
				System.setProperty("wordnet.database.dir", wordNetPath);
		database = WordNetDatabase.getFileInstance();
		
	}

	// not using wordsense on purpose
	public WordOfInterest getUsableForm(String word)
	{	
		List<String> candidates = new ArrayList<String>();
    	
		candidates.add(word);
    	// optimize here
		
    	boolean alreadyAtItsBaseForm = true;
    	for(SynsetType typ : SynsetType.ALL_TYPES)
    	{
    		String[] sArray = database.getBaseFormCandidates(word, typ);
    		if(sArray.length>0)
    			alreadyAtItsBaseForm = false;
    		for(int j = 0; j < sArray.length; j++)
    			candidates.add(sArray[j]);
    	}
    	
    	for(String candidate : candidates)
    	{
			Synset[] synsets = database.getSynsets(candidate);
    		if(synsets.length > 0)
    		{
    			//String myStr = synsets[0].getWordForms()[0];
    			String convertTo;
    			if(alreadyAtItsBaseForm)
    				convertTo = word;
    			else
    				convertTo = synsets[0].getWordForms()[0];
    			
    			return new WordOfInterest(word,synsets[0],convertTo);
    		}
    	}
  
		return new WordOfInterest(word, dummySynset, word);
	}
}
