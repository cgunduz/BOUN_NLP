package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.smu.tspell.wordnet.WordSense;

import opennlp.tools.util.Span;

import utils.entity.TokenContent;
import utils.entity.WordOfInterest;
import utils.opennlp.OpenNlpBacisUtils;
import utils.wordnet.WordNetUtils;

public class NlpUtils {

	private OpenNlpBacisUtils openNlpUtils;
	private WordNetUtils wordNetUtils;
	
	private final static String UTIL_PROPERTY_FILES = "nlpUtils.properties";
	private Properties nlpUtilProperties;
	
	public NlpUtils()
	{
		openNlpUtils = new OpenNlpBacisUtils();
		wordNetUtils = new WordNetUtils();
		
		nlpUtilProperties = new Properties();
		try {
			nlpUtilProperties.load(new FileInputStream(UTIL_PROPERTY_FILES));
		} catch (FileNotFoundException e) {
			System.out.println("properties not found nlpTemplate");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io exception when loading nlp template properties");
			e.printStackTrace();
		}
	}
	
	public List<WordOfInterest> tokenizeAndCleanText(String s)
	{
		// lower-case 
		s = s.toLowerCase();
		
		//tokenize  string
		String[] tokenized = openNlpUtils.genericlyTokenizeString(s);
		
		//disclude non informative words 
		List<Span> disclude = eliminateSpecificTokens(tokenized);
		
		Boolean[] tokenAcceptance = new Boolean[tokenized.length];
		Arrays.fill(tokenAcceptance, true);
		
		// Could be optimized, is there need ?
		for(Span sp : disclude)
		{
			for(int i = sp.getStart(); i<sp.getEnd(); i++)
			{
				tokenAcceptance[i] = false;
			}
		}
		
		// Map non discluded to list
		List<WordOfInterest> tokenizedList = new ArrayList<WordOfInterest>();
		for(int i = 0; i<tokenized.length; i++)
			if(tokenAcceptance[i])
				tokenizedList.add(wordNetUtils.getUsableForm(tokenized[i]));
		
		return tokenizedList;
	}
	
	private List<Span> eliminateSpecificTokens(String[] tokenized)
	{
		List<Span> disclude = new ArrayList<Span>();
		if(nlpUtilProperties.getProperty("eliminateNames").equals("1"))
		{
			Span[] span = openNlpUtils.findNamesInTokens(tokenized);
			for(Span sp : span)
			{
				disclude.add(sp);
			}
		}
		
		if(nlpUtilProperties.getProperty("eliminateOrganizations").equals("1"))
		{
			Span[] span = openNlpUtils.findOrganizationsInTokens(tokenized);
			for(Span sp : span)
			{
				disclude.add(sp);
			}
		}
		
		if(nlpUtilProperties.getProperty("eliminateMoney").equals("1"))
		{
			Span[] span = openNlpUtils.findMoneyInTokens(tokenized);
			for(Span sp : span)
			{
				disclude.add(sp);
			}
		}
		
		if(nlpUtilProperties.getProperty("eliminateLocations").equals("1"))
		{
			Span[] span = openNlpUtils.findLocationsInTokens(tokenized);
			for(Span sp : span)
			{
				disclude.add(sp);
			}
		}
		
		if(nlpUtilProperties.getProperty("eliminateDates").equals("1"))
		{
			Span[] span = openNlpUtils.findDatesInTokens(tokenized);
			for(Span sp : span)
			{
				disclude.add(sp);
			}
		}
		
		return disclude;
	}
}
