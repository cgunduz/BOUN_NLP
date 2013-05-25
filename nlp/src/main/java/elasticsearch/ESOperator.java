package elasticsearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.jackson.core.json.ReaderBasedJsonParser;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import com.google.gson.Gson;

import utils.entity.WordOfInterest;

import elasticsearch.entity.IndexData;
import elasticsearch.entity.Weight;

public class ESOperator {

	ESClient esClient;
	
	private static String INDEX_LOCATION = "test";
	
	Gson gson;
	
	public ESOperator() {
		
		esClient = new ESClient();
		gson = new Gson();
	}

	public static void main(String[] args) {
		
		Weight w = new Weight("Ekman", "anger", 1);
		WordOfInterest woi = new WordOfInterest("lol1", null, "lol2");
		WordOfInterest woi2 = new WordOfInterest("lol3", null, "lol4");
		
		List<Weight> wList = new ArrayList<Weight>();
		wList.add(w);
		List<WordOfInterest> woiList = new ArrayList<WordOfInterest>();
		woiList.add(woi);
		woiList.add(woi2);
		
		IndexData idd = new IndexData("emotion",woiList,wList);
		
		ESOperator myIndexer = new ESOperator();
		
		IndexResponse ir = myIndexer.index(idd);
		
		SearchResponse sr = myIndexer.searchByConversion("_very_good");
		
		SearchResponse sr2 = myIndexer.searchByOrigin("_very_awesome");
		
		/*
		SearchHits hits = sr2.getHits();
		
		System.out.println(hits.getTotalHits());
		for(SearchHit hit : hits)
		{
			String s = hit.getSourceAsString();
			System.out.println(s);
			
			String sgs = myIndexer.gson.fromJson(s, String.class);
			
			System.out.println(sgs);
		}
		
		*/
		
		System.out.println("done");
		
		String s = "{\"real\":\"_happyness_sadness\",\"Happyness\":3.0,\"conversion\":\"_happy_sad\"}";
		
		Gson gson = new Gson();
		Map<String,String> map = gson.fromJson(s, Map.class);
		System.out.println(	String.valueOf(map.get("Happyness")));
		System.out.println(	String.valueOf(map.get("joy")));
		
	}
	
	public IndexResponse index(IndexData data)
	{
		List<WordOfInterest> tokenList = data.getTokens();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String conversion = "";
		String real = "";
		for(WordOfInterest wi : tokenList)
		{	
			conversion += "_" + wi.getConversion();
			real += "_" + wi.getWord();
		}
		
		map.put("real", real);
		map.put("conversion", conversion);
		
		for(Weight w : data.getWeights())
		{
			map.put(w.getIdentifier(), w.getMultiplier());	
		}
		
		
        String json = gson.toJson(map);
        
        IndexResponse response = esClient.getClient().prepareIndex(INDEX_LOCATION, data.getType())
                .setSource(json)
                .execute()
                .actionGet();
        
        return response;
	}
	
	public SearchResponse searchByOrigin(String word)
	{
		return makeFullTextSearchUsingSingleField("real",word);
	}
	
	public SearchResponse searchByConversion(String word)
	{
		return makeFullTextSearchUsingSingleField("conversion",word);
	}
	
	private SearchResponse makeFullTextSearchUsingSingleField(String fieldName, String word)
	{
		SearchResponse response = esClient.getClient().prepareSearch(INDEX_LOCATION)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(QueryBuilders.termQuery(fieldName, word))
		        .execute()
		        .actionGet();
		
		return response;
	}
}
