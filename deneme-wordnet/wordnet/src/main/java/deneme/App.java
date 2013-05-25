package deneme;

import java.io.IOException;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * Hello world!
 *
 */
public class App 
{
	private static void printAll(String[] s, String printing)
	{
		System.out.println(printing + " : ");
		for(String str : s)
		{
			System.out.println(str);
		}
	}
	
    public static void main( String[] args )
    {
    	
    	System.out.println(System.getProperty("wordnet.database.dir"));
    	System.setProperty("wordnet.database.dir", "/home/cem/Developer/WordNet-3.0/dict");
    	System.out.println(System.getProperty("wordnet.database.dir"));
    	
    	NounSynset nounSynset;
    	NounSynset[] hyponyms;
    	
    	String daWord = "better";

    	WordNetDatabase database = WordNetDatabase.getFileInstance();
    	Synset[] synsets = database.getSynsets(daWord,SynsetType.ADJECTIVE);
    	
    	int synsetNo = 0;
    	for(Synset s : synsets)
    	{
    		System.out.println("----------------------------");
    		synsetNo++;
    		try{
    			System.out.println("def: " +  s.getDefinition());
    			System.out.println("tag count: " + s.getTagCount("best"));	
    			System.out.println("type: " + s.getType());
    			printAll(s.getUsageExamples(),"usage");
    			printAll(s.getWordForms(),"word forms");
    		}
    		catch(Exception e)
    		{
    			System.out.println("For : " + synsetNo);
    		}
    		
    		
    	}
    	
    	String[] sArray = null;
    	
    	for(SynsetType typ : SynsetType.ALL_TYPES)
    	{
    		sArray = database.getBaseFormCandidates(daWord, typ);
    		for(int j = 0; j < sArray.length; j++)
    			System.out.println(sArray[j]);
    	}
    	
    	System.out.println("new test");
    	
    	WordNetUtil util = new WordNetUtil("wordnet.database.dir");
    	
    	String testCase = "In a departure from the style of traditional encyclopedias, Wikipedia is largely open to editing. " +
    			"This means that, with the exception of particularly sensitive and/or";
    	
    	System.out.println(util.getUsableForm("editing").getWordForm());
    	
    }
}
