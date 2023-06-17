package org.example;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.*;

import java.io.IOException;


public class ElasticsearchConnectionCheckHTTP {

    public static void main(String[] args) throws IOException {
        String host = "115.68.193.101";
        int port = 19200;
        String username = "khsystems";
        String password = "qwer12#$";

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
            System.out.println("Connection is SUCCESSFUL.");
        } catch (IOException e) {
            System.out.println("Connection is FAILED. Exception: " + e.getMessage());
        } finally {
            client.close();
        }
    }
}