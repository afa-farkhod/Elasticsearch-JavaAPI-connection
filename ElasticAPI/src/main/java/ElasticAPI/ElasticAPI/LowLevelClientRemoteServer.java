package ElasticAPI.ElasticAPI;

import java.io.IOException;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;


public class LowLevelClientRemoteServer {

	public static void main(String[] args) throws IOException {
	    RestClient restClient = RestClient.builder(
	            new HttpHost("115.68.193.101", 19200, "http"))
	            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(
	                    new BasicCredentialsProvider() {{
	                        setCredentials(AuthScope.ANY,
	                                new UsernamePasswordCredentials("khsystems", "qwer12#$"));}})).build();

	    HttpEntity entity = new NStringEntity("{\n \"query\": {\n \"match\": {\n \"DestCityName\": \"Venice\"\n }\n }\n}", 
	    		ContentType.APPLICATION_JSON);

	    Response response = restClient.performRequest(
	            "GET",
	            "/kibana_sample_data_flights/_search",
	            Collections.emptyMap(),
	            entity);

	    String jsonResponse = EntityUtils.toString(response.getEntity());
	    ObjectMapper mapper = new ObjectMapper();
	    Object json = mapper.readValue(jsonResponse, Object.class);
	    String formattedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

	    System.out.println(formattedJson);

	    restClient.close();
	}
}

