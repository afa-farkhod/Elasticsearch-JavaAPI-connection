package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

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

public class ElasticHighLevelRemote {

    private static final boolean SearchHit = false;

    public static void main(String[] args) throws IOException {

//******************************ELASTIC & KIBANA (http://ipaddress)(High Level Client)*********************************//
//KibanaRestClient kibanaClient = KibanaRestClient.create(RestClient.builder(new HttpHost("ipaddress", port, "http")));

        Scanner input = new Scanner(System.in);
        System.out.print("Enter index: ");
        String indexName = input.nextLine();
        System.out.print("Enter field: ");
        String fieldName = input.nextLine();
        System.out.print("Enter value: ");
        String valueName = input.nextLine();

        //RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("ipaddress", port, "http"))
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("115.68.193.101", 19200, "http"))
                .setHttpClientConfigCallback(new HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                        credentialsProvider.setCredentials(AuthScope.ANY,
//                                new UsernamePasswordCredentials("username", "password"));
                                new UsernamePasswordCredentials("khsystems", "qwer12#$"));

                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        String field = fieldName;
        String value = valueName;

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
