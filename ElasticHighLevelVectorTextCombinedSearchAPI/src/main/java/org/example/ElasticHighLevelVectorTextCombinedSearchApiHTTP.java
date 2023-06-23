package org.example;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Scanner;

/*
 1. Elasticsearch Java High-Level REST Client to perform various search operations on an Elasticsearch index.
 2. Particularly provides a basic framework for performing vector search, text search, and combined search operations
    using the Elasticsearch Java High-Level REST Client
 */

public class ElasticHighLevelVectorTextCombinedSearchApiHTTP {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter index: ");
        String indexName = input.nextLine(); 

        System.out.print("Enter vector field: ");
        String vectorFieldName = input.nextLine();

        System.out.print("Enter vector value: ");
        String vectorValue = input.nextLine();

        System.out.print("Enter text field: ");
        String textFieldName = input.nextLine();

        System.out.print("Enter text value: ");
        String textValue = input.nextLine(); 

        RestHighLevelClient client = createClient();

        // Vector search
        SearchResponse vectorSearchResponse = performVectorSearch(client, indexName, vectorFieldName, vectorValue);

        // Text search
        SearchResponse textSearchResponse = performTextSearch(client, indexName, textFieldName, textValue);

        // Combined k-nearest neighbor search
        int k = 5; // Number of nearest neighbors
        SearchResponse combinedSearchResponse = performCombinedSearch(client, indexName, vectorFieldName, textFieldName, vectorValue, k);

        // Print the search results
        printSearchResults("Vector Search Results", vectorSearchResponse);
        printSearchResults("Text Search Results", textSearchResponse);
        printSearchResults("Combined Search Results", combinedSearchResponse);

        client.close();
    }

    private static RestHighLevelClient createClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("USERNAME", "PASSWORD"));

        RestClientBuilder builder = RestClient.builder(new HttpHost("ELASTIC-IP-ADDRESS", ELASTIC-PORT, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }

    private static SearchResponse performVectorSearch(RestHighLevelClient client, String indexName, String vectorField, String value)
            throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MoreLikeThisQueryBuilder vectorQuery = QueryBuilders.moreLikeThisQuery(new String[]{vectorField},
                null, new MoreLikeThisQueryBuilder.Item[]{new MoreLikeThisQueryBuilder.Item(indexName, value)});
        searchSourceBuilder.query(vectorQuery);

        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    private static SearchResponse performTextSearch(RestHighLevelClient client, String indexName, String textField, String value)
            throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder textQuery = QueryBuilders.matchQuery(textField, value);
        searchSourceBuilder.query(textQuery);

        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    private static SearchResponse performCombinedSearch(RestHighLevelClient client,
                        String indexName, String vectorField, String textField, String value, int k) throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        MoreLikeThisQueryBuilder vectorQuery = QueryBuilders.moreLikeThisQuery(new String[]{vectorField},
                null, new MoreLikeThisQueryBuilder.Item[]{new MoreLikeThisQueryBuilder.Item(indexName, value)});
        boolQuery.must(vectorQuery);

        MatchQueryBuilder textQuery = QueryBuilders.matchQuery(textField, value);
        boolQuery.must(textQuery);

        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.size(k); // Limit the number of results to k

        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    private static void printSearchResults(String title, SearchResponse searchResponse) {

        System.out.println(title);
        System.out.println("Total Hits: " + searchResponse.getHits().getTotalHits().value);
        System.out.println("Search Results:");
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String id = hit.getId();
            System.out.println("ID: " + id + ", Score: " + hit.getScore() + ", Source: " + hit.getSourceAsString());
        }

        System.out.println(new String(new char[45]).replace("\0", "\u2500"));
    }
}
