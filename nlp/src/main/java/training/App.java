package training;

import java.util.List;

import elasticsearch.entity.IndexData;
import elasticsearch.entity.Weight;
import training.entity.Trainable;
import utils.entity.WordOfInterest;

public class App {
	
	public static void main(String[] args) {
		
		Trainer t = new EmotionTrainer();
		List<Trainable> trainables = t.createTrainablesFromFile("emotion_training_file_path", "emotion_training_files");
		
		t.train(trainables);
		System.out.println("done");
	}
	
	public void emptyTest()
	{
		/*
		Trainer trainer = new Trainer();
		
		Trainable trainable = new Trainable();
		
		Weight w1 = new Weight("test_grand", "joy", 3.0);
		Weight w2 = new Weight("test_grand", "trust", 1.0);
		Weight w3 = new Weight("test_grand", "disgust", 1.0);
		
		trainable.getWeight().add(w1);
		trainable.getWeight().add(w2);
		trainable.getWeight().add(w3);
		
		trainable.setPhrase("Night is dark and full of terrors");
		
		trainable.setType("test_emotion");
		
		List<IndexData> indexDataTestList = trainer.trainableToIndexData(trainable);
		
		for(IndexData id : indexDataTestList)
		{
			for(WordOfInterest w : id.getTokens())
			{
				System.out.println(w.getWord());
			}
			System.out.println("-------------------");
		}
		*/
	}
}
