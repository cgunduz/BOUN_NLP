package detection.entity;

import java.util.ArrayList;
import java.util.List;

public class EmotionEvaluation {

	private List<GenericSearchResult> originalSearchResult;
	private List<GenericSearchResult> processedSearchResult;
	
	private String fullSearchQuery;
	private String originalSearchQuery;
	
	private double joy;
	private double sadness;
	private double trust;
	private double disgust;
	private double fear;
	private double anger;
	private double suprise;
	private double anticipation;
	private double general;
	
	private double total_hits;

	public EmotionEvaluation()
	{
		originalSearchResult = new ArrayList<GenericSearchResult>();
		processedSearchResult = new ArrayList<GenericSearchResult>();
	}
	
	public List<GenericSearchResult> getProcessedSearchResult() {
		return processedSearchResult;
	}

	public void setProcessedSearchResult(
			List<GenericSearchResult> processedSearchResult) {
		this.processedSearchResult = processedSearchResult;
	}

	public String getFullSearchQuery() {
		return fullSearchQuery;
	}

	public void setFullSearchQuery(String fullSearchQuery) {
		this.fullSearchQuery = fullSearchQuery;
	}

	public String getOriginalSearchQuery() {
		return originalSearchQuery;
	}

	public void setOriginalSearchQuery(String originalSearchQuery) {
		this.originalSearchQuery = originalSearchQuery;
	}

	public double getJoy() {
		return joy;
	}

	public void setJoy(double joy) {
		this.joy = joy;
	}

	public double getSadness() {
		return sadness;
	}

	public void setSadness(double sadness) {
		this.sadness = sadness;
	}

	public double getTrust() {
		return trust;
	}

	public void setTrust(double trust) {
		this.trust = trust;
	}

	public double getDisgust() {
		return disgust;
	}

	public void setDisgust(double disgust) {
		this.disgust = disgust;
	}

	public double getFear() {
		return fear;
	}

	public void setFear(double fear) {
		this.fear = fear;
	}

	public double getAnger() {
		return anger;
	}

	public void setAnger(double anger) {
		this.anger = anger;
	}

	public double getSuprise() {
		return suprise;
	}

	public void setSuprise(double suprise) {
		this.suprise = suprise;
	}

	public double getAnticipation() {
		return anticipation;
	}

	public void setAnticipation(double anticipation) {
		this.anticipation = anticipation;
	}

	public double getGeneral() {
		return general;
	}

	public void setGeneral(double general) {
		this.general = general;
	}

	public double getTotal_hits() {
		return total_hits;
	}

	public void setTotal_hits(double total_hits) {
		this.total_hits = total_hits;
	} 
	
	public List<GenericSearchResult> getOriginalSearchResult() {
		return originalSearchResult;
	}

	public void setOriginalSearchResult(
			List<GenericSearchResult> originalSearchResult) {
		this.originalSearchResult = originalSearchResult;
	}
	
	
	
}
