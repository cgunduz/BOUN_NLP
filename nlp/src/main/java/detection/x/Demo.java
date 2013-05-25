package detection.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import detection.entity.DeleteMe3;
import detection.entity.EmotionEvaluation;
import detection.entity.GenericSearchResult;

public class Demo {

	private Evaluator evaluator;
	private EmotionMapper mapper;
	
	List<String> listOfEmotionsInOrder;
	
	public Demo()
	{
		evaluator = new Evaluator();
		mapper = new EmotionMapper();
		
		listOfEmotionsInOrder = new ArrayList<String>();
		listOfEmotionsInOrder.add("Joy");
		listOfEmotionsInOrder.add("Sadness");
		listOfEmotionsInOrder.add("Trust");
		listOfEmotionsInOrder.add("Disgust");
		listOfEmotionsInOrder.add("Fear");
		listOfEmotionsInOrder.add("Anger");
		listOfEmotionsInOrder.add("Suprise");
		listOfEmotionsInOrder.add("Anticipation");
	}
	
	public List<String> simpleSortAndSendForDemoOnly(List<DeleteMe3> emotionResults)
	{
		String HighestEmotion = "";
		String tailing = "";
		Double highestValue = 0.0;
		Double secondHighestValue = 0.0;
		for(DeleteMe3 deleteMe3 : emotionResults)
		{
			if(deleteMe3.getValue() > highestValue)
			{
				tailing = HighestEmotion;
				HighestEmotion = deleteMe3.getEmotion();
				secondHighestValue = highestValue;
				highestValue = deleteMe3.getValue();
			}
			else if(deleteMe3.getValue() > secondHighestValue)
			{
				secondHighestValue = deleteMe3.getValue();
				tailing = deleteMe3.getEmotion();
			}
		}
		
		return Arrays.asList(HighestEmotion,tailing);
	}
	
	public List<String> initateSearchRequest(String s)
	{
		List<GenericSearchResult> resultSet = evaluator.searchMaker(s,false);
		EmotionEvaluation eve = mapper.mapGenericSearchResultSetToEmotionEvaluation(resultSet);
		
		String result_log = "Final Results : ";
		result_log += "\nTotal Hits : " + eve.getTotal_hits() + "\n";
		result_log += "\nJoy : " + eve.getJoy();
		result_log += "\nSadness : " + eve.getSadness();
		result_log += "\nTrust : " + eve.getTrust();
		result_log += "\nDisgust : " + eve.getDisgust();
		result_log += "\nFear : " + eve.getFear();
		result_log += "\nAnger : " + eve.getAnger();
		result_log += "\nSurprise : " + eve.getSuprise();
		result_log += "\nAnticipation : " + eve.getAnticipation();
		
		String processed_log = "Sub-Results : ";
		
		int counter = 0;
		for(GenericSearchResult res : eve.getProcessedSearchResult())
		{
			processed_log += "\nSub Result no " + counter + " : ";
			processed_log += "\nSub Result total hits : " + res.getTotalHits();
			processed_log += "\nSearch query : " + res.getSearchQuery();
			processed_log += "\nStatistics : " + res.getResultMap().toString();
			counter++;
		}
		
		String unprocessed_log = "Search-Results : ";
		
		counter = 0;
		for(GenericSearchResult res : eve.getOriginalSearchResult())
		{
			unprocessed_log += "\nSearch Result no " + counter + " : ";
			unprocessed_log += "\nSearch query : " + res.getSearchQuery();
			unprocessed_log += "\nStatistics : " + res.getResultMap().toString(); 
			counter++;
		}
		
		System.out.println(result_log);
		System.out.println("\n\n");
		System.out.println("---------------");
		System.out.println(processed_log);
		System.out.println("\n\n");
		System.out.println("---------------");
		System.out.println(unprocessed_log);
		System.out.println("\n\n");
		System.out.println("---------------");
		
		/* DELETE HERE */
		
		
		
		List<DeleteMe3> tempList = new ArrayList<DeleteMe3>();
		tempList.add(new DeleteMe3("Joy" ,eve.getJoy()));
		tempList.add(new DeleteMe3("Sadness" ,eve.getSadness()));
		tempList.add(new DeleteMe3("Trust" ,eve.getTrust()));
		tempList.add(new DeleteMe3("Disgust" ,eve.getDisgust()));
		tempList.add(new DeleteMe3("Fear" ,eve.getFear()));
		tempList.add(new DeleteMe3("Anger" ,eve.getAnger()));
		tempList.add(new DeleteMe3("Surprise" ,eve.getSuprise()));
		tempList.add(new DeleteMe3("Anticipation" ,eve.getAnticipation()));
		
		if(eve.getTotal_hits() < 5)
			return Arrays.asList("Unknown" , "Unknown");
		
		return simpleSortAndSendForDemoOnly(tempList);
		
		/* DELETE HERE */
	}
}
