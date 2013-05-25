package utils;

import java.util.Iterator;
import java.util.List;

import utils.entity.WordOfInterest;

import edu.smu.tspell.wordnet.WordSense;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
	static String testCase1 = "It's been a hard days night, and I've been drifting like a dog";
	
	static String testCase2 = "Hello, My name is Cem. I like to boogie and get down baby.";
	
	static String testCase3 = "This is my most favourite post I've ever written. Read, comment, nod, agree, laugh, disagree. http://gingergirlsays.com/women-mental/  #bbloggers #love";
	
	static String testCase4 = "The history of NLP generally starts in the 1950s, although work can be found from earlier periods. In 1950, Alan Turing published his famous article \"Computing Machinery and Intelligence\" which proposed what is now called the Turing test as a criterion of intelligence.";
   
	public static void main(String[] args) {
		
    	System.out.println("Original text : " + testCase4);
    	
    	NlpUtils nlpUtils = new NlpUtils();
    	
    	List<WordOfInterest> list = nlpUtils.tokenizeAndCleanText(testCase4);
    	
    	Iterator<WordOfInterest> it = list.iterator();
    	while(it.hasNext())
    	{
    		WordOfInterest caseInterest = it.next();
    		if(caseInterest.getSynset().getWordForms()[0].equals("dummy"))
    			caseInterest.getSynset().getWordForms()[0] = caseInterest.getWord();
    		System.out.println(caseInterest.getWord() + " - " + caseInterest.getConversion());
    	}
	}
}
