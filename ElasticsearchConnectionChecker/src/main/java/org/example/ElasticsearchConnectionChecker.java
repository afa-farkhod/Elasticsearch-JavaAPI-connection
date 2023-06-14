package org.example;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.*;

import java.io.IOException;


public class ElasticsearchConnectionChecker {

    public static void main(String[] args) throws IOException {
        String host = "115.68.193.101";                                        // <<<<<<<<<<<<<< Replace with the Elasticsearch host IP address
        int port = 19200;                                                      // <<<<<<<<<<<<<< Replace Elasticsearch port
        String username = "khsystems";                                         //<<<<<<<<<<<<<< Replace with your Elasticsearch username
        String password = "qwer12#$";                                          //<<<<<<<<<<<<<< Replace with your Elasticsearch password

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY,
                            new UsernamePasswordCredentials(username, password));
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                });

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);

        try {
            client.ping(RequestOptions.DEFAULT);
            System.out.println("Connection to Elasticsearch server is SUCCESSFUL.");
        } catch (IOException e) {
            System.out.println("Failed to connect to Elasticsearch server. Exception: " + e.getMessage());
        } finally {
            client.close();
        }
    }
}