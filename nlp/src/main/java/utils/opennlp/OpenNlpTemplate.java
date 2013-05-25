package utils.opennlp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class OpenNlpTemplate {

	private final static String TEMPLATE_PROPERTY_FILES = "nlpTemplate.properties";
	
	private Properties nlpTemplateProperties;
	
	private String pathToTrainingFiles;
	private String sentenceTrainingFile;
	private String tokenTrainingFile;
	
	private String dateNameTrainingFile;
	private String locationNameTrainingFile;
	private String moneyNameTrainingFile;
	private String organizationNameTrainingFile;
	private String personNameTrainingFile;
	
	private InputStream trainingInputStream;
	private SentenceModel sentenceModel;
	
	private InputStream tokenInputStream;
	private TokenizerModel tokenModel;
	
	private InputStream dateNameInputStream;
	private TokenNameFinderModel dateTokenNameFinderModel;
	
	private InputStream locationNameInputStream;
	private TokenNameFinderModel locationTokenNameFinderModel;
	
	private InputStream moneyNameInputStream;
	private TokenNameFinderModel moneyTokenNameFinderModel;
	
	private InputStream organizationNameInputStream;
	private TokenNameFinderModel organizationTokenNameFinderModel;
	
	private InputStream personNameInputStream;
	private TokenNameFinderModel personTokenNameFinderModel;

	public OpenNlpTemplate() {
		
		nlpTemplateProperties = new Properties();
		try {
			nlpTemplateProperties.load(new FileInputStream(TEMPLATE_PROPERTY_FILES));
		} catch (FileNotFoundException e) {
			System.out.println("properties not found nlpTemplate");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io exception when loading nlp template properties");
			e.printStackTrace();
		}
		
		pathToTrainingFiles = nlpTemplateProperties.getProperty("pathToTrainingFiles");
		sentenceTrainingFile = nlpTemplateProperties.getProperty("sentenceTrainingFile");
		tokenTrainingFile = nlpTemplateProperties.getProperty("tokenTrainingFile");
		
		dateNameTrainingFile = nlpTemplateProperties.getProperty("dateNameTrainingFile");
		locationNameTrainingFile = nlpTemplateProperties.getProperty("locationNameTrainingFile");
		moneyNameTrainingFile = nlpTemplateProperties.getProperty("organizationNameTrainingFile");
		organizationNameTrainingFile = nlpTemplateProperties.getProperty("moneyNameTrainingFile");
		personNameTrainingFile = nlpTemplateProperties.getProperty("personNameTrainingFile");
		
		
		try {
			trainingInputStream = new FileInputStream(appendTrainingPath(sentenceTrainingFile));
			tokenInputStream = new FileInputStream(appendTrainingPath(tokenTrainingFile));
			
			dateNameInputStream = new FileInputStream(appendTrainingPath(dateNameTrainingFile));
			locationNameInputStream = new FileInputStream(appendTrainingPath(locationNameTrainingFile));
			moneyNameInputStream = new FileInputStream(appendTrainingPath(moneyNameTrainingFile));
			organizationNameInputStream = new FileInputStream(appendTrainingPath(organizationNameTrainingFile));
			personNameInputStream = new FileInputStream(appendTrainingPath(personNameTrainingFile));
		} catch (FileNotFoundException e) {
			System.out.println("training binary not found for a file");
			e.printStackTrace();
		}
		
		try {
			sentenceModel =  new SentenceModel(trainingInputStream);
			tokenModel = new TokenizerModel(tokenInputStream);
			
			dateTokenNameFinderModel = new TokenNameFinderModel(dateNameInputStream);
			locationTokenNameFinderModel = new TokenNameFinderModel(locationNameInputStream);
			moneyTokenNameFinderModel = new TokenNameFinderModel(moneyNameInputStream);
			organizationTokenNameFinderModel = new TokenNameFinderModel(organizationNameInputStream);
			personTokenNameFinderModel = new TokenNameFinderModel(personNameInputStream);
		} catch (InvalidFormatException e) {
			System.out.println("model init failure");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("model init failure");
			e.printStackTrace();
		}
		
	}
	
	public TokenNameFinderModel getDateTokenNameFinderModel() {
		return dateTokenNameFinderModel;
	}

	public TokenNameFinderModel getLocationTokenNameFinderModel() {
		return locationTokenNameFinderModel;
	}

	public TokenNameFinderModel getMoneyTokenNameFinderModel() {
		return moneyTokenNameFinderModel;
	}

	public TokenNameFinderModel getOrganizationTokenNameFinderModel() {
		return organizationTokenNameFinderModel;
	}

	public TokenNameFinderModel getPersonTokenNameFinderModel() {
		return personTokenNameFinderModel;
	}
	
	public TokenizerModel getTokenModel() {
		return tokenModel;
	}

	public SentenceModel getSentenceModel() {
		return sentenceModel;
	}
	
	private String appendTrainingPath(String s)
	{
		return pathToTrainingFiles + s;
	}
}
