package training;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import training.entity.Trainable;

import elasticsearch.entity.Weight;

// EPIC CLASS
public class EmotionTrainer extends Trainer {
	
	// NOT PROUD .
	@Override
	public List<Trainable> createTrainablesFromFile(String filepathProperty,
			String filenameProperty) {
		// TODO Auto-generated method stub
		String path = super.getTrainerProperties().getProperty(filepathProperty);
		String commaSeperatedFiles = super.getTrainerProperties().getProperty(filenameProperty);
		List<String> files = Arrays.asList(commaSeperatedFiles.split(","));
		
		List<Trainable> indexDataList = new ArrayList<Trainable>();
		
		/* OH JOY
		 * GLOBALS CALCULATED HERE
		 */
		Double global_joy = 0.0;
		Double global_sadness = 0.0;
		Double global_trust = 0.0;
		Double global_disgust = 0.0;
		Double global_fear = 0.0;
		Double global_anger = 0.0;
		Double global_suprise = 0.0;
		Double global_anticipation = 0.0;
		Double global_general = 0.0;
		Double global_total_files = 0.0;
		
		for(String file : files)
		{
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(path + file));
				String line;
				while ((line = br.readLine()) != null) {
				  
					String phrase = line;
					br.readLine();
					Double joy = getValue(br.readLine());
					Double sadness = getValue(br.readLine());
					Double trust = getValue(br.readLine());
					Double disgust = getValue(br.readLine());
					Double fear = getValue(br.readLine());
					Double anger = getValue(br.readLine());
					Double suprise = getValue(br.readLine());
					Double anticipation = getValue(br.readLine());
					Double general = getValue(br.readLine());
					
					global_joy += joy;
					global_sadness += sadness;
					global_trust += trust;
					global_disgust += disgust;
					global_fear += fear;
					global_anger += anger;
					global_suprise += suprise;
					global_anticipation += anticipation;
					global_general += general;
					global_total_files++;
					
					
					Weight w1 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "joy", joy);
					
					Weight w2 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "sadness", sadness);
					
					Weight w3 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "trust", trust);
					
					Weight w4 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "disgust", disgust);
					
					Weight w5 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "fear", fear);
					
					Weight w6 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "anger", anger);
					
					Weight w7 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "suprise", suprise);
					
					Weight w8 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "anticipation", anticipation);
					
					Weight w9 = new Weight(super.getTrainerProperties()
							.getProperty("trainingType"), "general", general);
					
					Trainable t = new Trainable();
					t.getWeight().add(w1);
					t.getWeight().add(w2);
					t.getWeight().add(w3);
					t.getWeight().add(w4);
					t.getWeight().add(w5);
					t.getWeight().add(w6);
					t.getWeight().add(w7);
					t.getWeight().add(w8);
					t.getWeight().add(w9);
					
					t.setPhrase(phrase);
					t.setType(super.getTrainerProperties()
							.getProperty("trainingType"));
					
					indexDataList.add(t);
					
					br.readLine();
				}
				
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		System.out.println("global_joy : " + global_joy);
		System.out.println("global_sadness : " + global_sadness);
		System.out.println("global_trust : " + global_trust);
		System.out.println("global_disgust : " + global_disgust);
		System.out.println("global_fear : " + global_fear);
		System.out.println("global_anger : " + global_anger);
		System.out.println("global_suprise : " + global_suprise);
		System.out.println("global_anticipation : " + global_anticipation);
		System.out.println("global_general : " + global_general);
		System.out.println("global_total_files : " + global_total_files);
		
		return indexDataList;
	}

	private Double getValue(String s)
	{
		try{
			return Double.valueOf(s.substring(s.indexOf(':') + 1 , s.indexOf(':') + 2));
		}
		catch(Exception e)
		{
			//System.out.println(s);
		}
		
		return 0.0;
	}
	
}
