package ElasticAPI.ElasticAPI;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class QueryLocalHost {

    private static final boolean SearchHit = false;

	public static void main(String[] args) throws IOException {

		nested query using elasticsearch java api and print results to console
    	connection with the elasticsearch server running in localhost in port: 9200
    	RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    	
    	//search request object for index "countries" from the elasticsearch
        SearchRequest searchRequest = new SearchRequest();
        //indices method is used to set the index to search on to "countries"
        searchRequest.indices("countries");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
         
        //nested path for "country.city"
        String nestedPath="country.city";
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        
        //match nested query search for contry.type = "USA" , elastic index is used in nested mapping
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("country.city.type", "Uzbekistan");
        NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(nestedPath, boolQueryBuilder.must(matchQuery), ScoreMode.None);
        //ecexute the query
        searchSourceBuilder.query(nestedQuery);
        //search request
        searchRequest.source(searchSourceBuilder);
        Map<String, Object> map=null;
          
        try { 
        	//search request is sent to elasticsearch using the high level client and results are stored in searchResponse object
        	SearchResponse searchResponse = null;
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                	String id = hit.getId();
                    map = hit.getSourceAsMap();
                      System.out.println("output: ID = " + id + ", " + Arrays.toString(map.entrySet().toArray()));
                }
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
/*******************************************************************************************************/
         
    }
}