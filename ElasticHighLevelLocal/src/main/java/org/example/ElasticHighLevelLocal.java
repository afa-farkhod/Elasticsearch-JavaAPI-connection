package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ElasticHighLevelLocal {

    private static final boolean SearchHit = false;

    public static void main(String[] args) {

        // Create the Elasticsearch client
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        // Define the search request
        //flowers_datset is the index name in the dataset that we've ingested to Elasticsearch
        SearchRequest searchRequest = new SearchRequest("flowers_dataset");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //climate is the field name in the index and temperate is the value
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("climate", "Temperate");
        boolQueryBuilder.must(matchQuery);

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(10); // set the maximum number of search hits to return
        searchRequest.source(searchSourceBuilder);

        try {
            // Execute the search request
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            // Process the search results
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for (SearchHit hit : searchHits) {
                String id = hit.getId();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                System.out.println("ID: " + id + ", Source: " + sourceAsMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the Elasticsearch client gracefully
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
