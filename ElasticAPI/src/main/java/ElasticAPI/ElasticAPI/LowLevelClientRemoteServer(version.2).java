package ElasticAPI.ElasticAPI;

import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

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

import com.fasterxml.jackson.databind.ObjectMapper;


public class LowLevelClientRemoteServer {

	public static void main(String[] args) throws IOException {
	    Scanner input = new Scanner(System.in);
	    System.out.print("enter index ");
	    String indexInput = input.nextLine();
	    System.out.print("enter id ");
	    String id = input.nextLine();
		RestClient restClient = RestClient.builder(
	            new HttpHost("ipaddress", port, "http"))
	            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(
	                    new BasicCredentialsProvider() {{
	                        setCredentials(AuthScope.ANY,
	                                new UsernamePasswordCredentials("username", "password"));}})).build();

	    HttpEntity entity = new NStringEntity("{\n \"query\": {\n \"match\": {\n \"_id\": \"" + id + "\"\n }\n }\n}", 
	    		ContentType.APPLICATION_JSON);

	    Response response = restClient.performRequest(
	            "GET", "/"+indexInput+"/_search",
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

