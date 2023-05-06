package org.example;

import java.io.IOException;
import java.util.Collections;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class ElasticLowLevelLocal {

    public static void main(String[] args) throws IOException {

        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();

        //flowers_datset is the index name and bL4L84cBG--CL3Mv4vrr is the id of the data in the elasticsearch
        HttpEntity entity = new NStringEntity("{\n  \"query\": {\n \"match\": {\n \"_id\": \"bL4L84cBG--CL3Mv4vrr\"\n }\n }\n}", ContentType.APPLICATION_JSON);

        Response response = restClient.performRequest(
                "GET",
                "/flowers_dataset/_search",
                Collections.emptyMap(), entity);

        System.out.println(EntityUtils.toString(response.getEntity()));

        restClient.close();
    }
}
