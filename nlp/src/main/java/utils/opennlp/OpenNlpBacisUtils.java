package utils.opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.util.Span;

public class OpenNlpBacisUtils {

	OpenNlpTemplate openNlpTemplate;
	SentenceDetectorME sentenceDetector;
	Tokenizer tokenizer;
	
	NameFinderME personNameFinder;
	NameFinderME dateNameFinder;
	NameFinderME locationNameFinder;
	NameFinderME organizationNameFinder;
	NameFinderME moneyNameFinder;

	public OpenNlpBacisUtils() {
		
		openNlpTemplate = new OpenNlpTemplate();
		sentenceDetector = new SentenceDetectorME(openNlpTemplate.getSentenceModel());
		tokenizer = new TokenizerME(openNlpTemplate.getTokenModel());
		
		personNameFinder = new NameFinderME(openNlpTemplate.getPersonTokenNameFinderModel());
		dateNameFinder = new NameFinderME(openNlpTemplate.getDateTokenNameFinderModel());
		locationNameFinder = new NameFinderME(openNlpTemplate.getLocationTokenNameFinderModel());
		organizationNameFinder = new NameFinderME(openNlpTemplate.getOrganizationTokenNameFinderModel());
		moneyNameFinder = new NameFinderME(openNlpTemplate.getMoneyTokenNameFinderModel());
		
	}
	
	public String[] detectSentencesInAString(String s)
	{
		return sentenceDetector.sentDetect(s);
	}
	
	public String[] genericlyTokenizeString(String s)
	{
		return tokenizer.tokenize(s);
	}
	
	public Span[] findNamesInTokens(String[] s)
	{
		return personNameFinder.find(s);
	}
	
	public Span[] findLocationsInTokens(String[] s)
	{
		return locationNameFinder.find(s);
	}
	
	public Span[] findDatesInTokens(String[] s)
	{
		return dateNameFinder.find(s);
	}
	
	public Span[] findOrganizationsInTokens(String[] s)
	{
		return organizationNameFinder.find(s);
	}
	
	public Span[] findMoneyInTokens(String[] s)
	{
		return moneyNameFinder.find(s);
	}
}
