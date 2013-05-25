package detection.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericSearchResult {

	private String searchQuery;
	private long totalHits;
	private double globalWeightQuantifier; // Not used
	
	private Map<String,Double> resultMap;
	
	public GenericSearchResult()
	{
		resultMap = new HashMap<String,Double>();
	}
	
	public GenericSearchResult(GenericSearchResult copy)
	{
		this.searchQuery = copy.getSearchQuery();
		this.totalHits = copy.getTotalHits();
		this.globalWeightQuantifier = copy.getGlobalWeightQuantifier();
		
		Map<String,Double> map = new HashMap<String,Double>();
		
		List<String> keys = this.getCriteria();
		for(String key : keys)
		{
			map.put(key, copy.resultMap.get(key));
		}
		
		this.resultMap = map;
	}
	
	public String getSearchQuery() {
		return searchQuery;
	}
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}
	public Long getTotalHits() {
		return totalHits;
	}
	public void setTotalHits(Long totalHits) {
		this.totalHits = totalHits;
	}
	public Double getGlobalWeightQuantifier() {
		return globalWeightQuantifier;
	}
	public void setGlobalWeightQuantifier(Double globalWeightQuantifier) {
		this.globalWeightQuantifier = globalWeightQuantifier;
	}
	
	public Map<String, Double> getResultMap() {
		return resultMap;
	}
	
	abstract public List<String> getCriteria();
	
}
