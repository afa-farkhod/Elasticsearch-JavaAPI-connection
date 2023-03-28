package ElasticAPI.ElasticAPI;

import java.io.IOException;
import java.util.Collections;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

	public class LowLevelClientLocalServer {

			 public static void main(String[] args) throws IOException {

	        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
	        
	        HttpEntity entity = new NStringEntity("{\n  \"query\": {\n \"match_all\": {}\n }\n}", ContentType.APPLICATION_JSON);
	        
	        Response response = restClient.performRequest(
	                "GET",
	                "/countries/_doc/Farkhod_Abdukodirov",
	                Collections.emptyMap());
	        
	        System.out.println(EntityUtils.toString(response.getEntity()));
	        	       
	        restClient.close();
	    }
	}