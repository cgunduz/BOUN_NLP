package utils.wordnet;

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
    	WordNetUtils util = new WordNetUtils(null);
    	
    	String testCase = "In a departure from the style of traditional encyclopedias, Wikipedia is largely open to editing. " +
    			"This means that, with the exception of particularly sensitive and/or";
    	
    	System.out.println(util.getUsableForm("editing").getWord());
    	
    }
}
