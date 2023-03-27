package ElasticAPI.ElasticAPI;

import java.io.IOException;
import java.util.Arrays;

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
import org.elasticsearch.common.collect.Map;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class HighLevelClientRemoteServer {

    private static final boolean SearchHit = false;

	public static void main(String[] args) throws IOException {
		
/******************************ELASTIC & KIBANA (http://115.68.193.101)(High Level Client)*********************************/
//elastic: http://115.68.193.101:19200/
//kibana: http://115.68.193.101:15601/
//KibanaRestClient kibanaClient = KibanaRestClient.create(RestClient.builder(new HttpHost("115.68.193.101", 15601, "http")));
		

    	RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("ipaddress", port, "http"))
    					.setHttpClientConfigCallback(new HttpClientConfigCallback() {
    						@Override
    						public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
    							CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    							credentialsProvider.setCredentials(AuthScope.ANY,
    									new UsernamePasswordCredentials("username", "password"));
    							
    							return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    						}
    					}));
    	
    	SearchRequest searchRequest = new SearchRequest();
    	searchRequest.indices("kibana_sample_data_flights"); 
    	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    	 
    	String field = "DestCityName";
    	String value = "London";

    	MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(field, value); 
    	searchSourceBuilder.query(matchQuery);
    	searchRequest.source(searchSourceBuilder);
    	
    	java.util.Map<String, Object> map=null;
    	 
    	try { 
        	SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHits = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHits) {
                	String id = hit.getId();
                    map = hit.getSourceAsMap();
                    System.out.println("output: ID = " + id + ", " + Arrays.toString(map.entrySet().toArray()));
                }
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	client.close();
    }
}
