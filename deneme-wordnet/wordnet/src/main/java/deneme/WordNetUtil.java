package deneme;

import java.util.ArrayList;
import java.util.List;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;

public class WordNetUtil {
	
	public WordNetUtil(String wordNetPath) {
		
		if(System.getProperty("wordnet.database.dir") == null)
			System.setProperty("wordnet.database.dir", wordNetPath);	
	}

	public WordSense getUsableForm(String word)
	{
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		
		List<String> candidates = new ArrayList<String>();
    	
    	for(SynsetType typ : SynsetType.ALL_TYPES)
    	{
    		String[] sArray = database.getBaseFormCandidates(word, typ);
    		for(int j = 0; j < sArray.length; j++)
    			candidates.add(sArray[j]);
    	}
		
    	candidates.add(word);
    	
    	for(String candidate : candidates)
    	{
			Synset[] synsets = database.getSynsets(candidate);
    		if(synsets.length > 0)
    		{
    			return new WordSense(candidate,synsets[0]);
    		}
    	}
    	
		return new WordSense(word, null);
	}
}
