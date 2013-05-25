package utils.opennlp;

import opennlp.tools.util.Span;

public class App 
{
    public static void main( String[] args )
    {
    	OpenNlpBacisUtils utils = new OpenNlpBacisUtils();
    	
    	/*String [] GimmeSentencesBitch = utils.detectSentencesInAString("The easiest way to try out the tokenizers are the command line tools. The tools are only intended for demonstration and testing. There are two tools, one for the Simple Tokenizer and one for the learnable tokenizer. A command line tool the for the Whitespace Tokenizer does not exist, because the whitespace separated output would be identical to the input. The following command shows how to use the Simple Tokenizer Tool. ");
    	
    	for(String s : GimmeSentencesBitch)
    	{
    		System.out.println(s);
    	}*/
    	
    	/*String[] tokenizerTest = utils.genericlyTokenizeString("$ bin/opennlp TokenizerTrainer -encoding UTF-8 -lang en -alphaNumOpt ");
    	
    	for(String s : tokenizerTest)
    	{
    		System.out.println(s);
    	}*/
    	
    	/*
    	String s = "But then all of a sudden we discovered that the john Kenway, our assassin hero, was not " +
    	"actually a assassin at all but a damned templar. His son Connor was going to beat his ass one day.";
    	
    	String[] tokens = utils.genericlyTokenizeString(s);
    	
    	Span[] spanArray = utils.findNamesInTokens(tokens);
    	// nameFinder.clearAdaptiveData()
    	
    	System.out.println(spanArray.length);
    	
    	for(Span span : spanArray)
    	{
    		System.out.println(span.toString());
    	}
    	*/
    	
    	
    }
}
