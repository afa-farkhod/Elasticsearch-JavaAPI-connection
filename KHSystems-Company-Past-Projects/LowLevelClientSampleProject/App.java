package com.example;

import java.util.HashMap;
import java.util.Map;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;


public class App 
{
   
    public static void main( String[] args )
    {
     
        test13();
        test08();

    }


    public static void test13()
    {
        //id pass
        final CredentialsProvider credentialsProvider =    new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,    new UsernamePasswordCredentials("elastic", "elastic"));

        //ip port
        //port : 59200  -> 7.16.3
        //port : 49200 -> 7.8

        //RestClientBuilder builder = RestClient.builder(new HttpHost("115.93.184.3", 59200))
        RestClientBuilder builder = RestClient.builder(new HttpHost("115.93.184.3", 59200))
        .setHttpClientConfigCallback(new HttpClientConfigCallback() {
                @Override
            public HttpAsyncClientBuilder customizeHttpClient(
                    HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder
                    .setDefaultCredentialsProvider(credentialsProvider);
            }
        });

        RestClient restClient = builder.build();

        Request request = new Request("GET", "/auto_keyword/_search/template");

        //request body
        /*
        {
            "id": "sample_0001",
            "params": {
              "keyword": "애터미"
            }
          }
        */

        //lowlevel 
        Gson Agson = new Gson();

        Map<String, Object> map = new HashMap<>();
        map.put("id", "sample_0001");
        
        Map<String, Object> term_1 = new HashMap<>();
        term_1.put( "keyword" , "애터미" );

        Map<String, Object> term_2 = new HashMap<>();
        term_2.put("params", term_1);

        //두개 map 합치기
        map.putAll(term_2);

        //json string으로 변환
        String mapString = Agson.toJson(map);

        //requeyst body 에 작성한 mapping 싣기 
        request.setJsonEntity(mapString);


        try{

            Response response = restClient.performRequest(request);

            //반환값 httpEntity -> string 변환하여 전달 (바로 json 으로 바꿀경우 문제발생  -> " <- 이스케이프 문제   )
            String responseBody = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")); 
            //System.out.println("response --> " + responseBody)


            clearScreen();
            //string -> json 으로 가져오기 
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(responseBody, JsonNode.class);

            //문자로 json  key 로 value 가져오기 
            JsonNode brandNode = jsonNode.get("took");
            String took = brandNode.asText();
            System.out.println("took = " + took);

            //hits -> hits
            JsonNode hits = jsonNode.get("hits");
            JsonNode childHits = hits.get("hits");
            int hitsNum = childHits.size();

            //return value
            /*
                "_source": {
                    "id": "238756802",
                    "name": "애터미 헤모힘*1set"
                },
                "fields": {
                    "id": [
                        "238756802"
                    ]
                },
                "highlight": {
                    "name.ngram": [
                        "<em>애터미 </em>헤모힘*1set"
                    ]
            */

            
            System.out.println("start : 13");
            System.out.println(" ");

            //반복되어야 하는 부분
            for (int i = 0; i <hitsNum; i++) {
              
              JsonNode childHit = childHits.get(i);//배열
              JsonNode _source = childHit.get("_source");

              JsonNode id = _source.get("id");
              System.out.println("id = " + id.asText());
              JsonNode name = _source.get("name");
              System.out.println("name = " + name.asText());
              

              JsonNode highlight = childHit.get("highlight");
              JsonNode name_ngram = highlight.get("name.ngram");
              for (JsonNode jsonNode2 : name_ngram) {
                  System.out.println("name.ngram = " + jsonNode2.asText());
              }

            }

            
            System.out.println(" ");
            System.out.println("end : 13");

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println( "error : "+ e.getMessage() );
        }



    }

    public static void test08()
    {
        //id pass
        final CredentialsProvider credentialsProvider =    new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,    new UsernamePasswordCredentials("elastic", "elastic"));

        //ip port
        //port : 59200  -> 7.16.3
        //port : 49200 -> 7.8

        //RestClientBuilder builder = RestClient.builder(new HttpHost("115.93.184.3", 59200))
        RestClientBuilder builder = RestClient.builder(new HttpHost("115.93.184.3", 49200))
        .setHttpClientConfigCallback(new HttpClientConfigCallback() {
                @Override
            public HttpAsyncClientBuilder customizeHttpClient(
                    HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder
                    .setDefaultCredentialsProvider(credentialsProvider);
            }
        });

        RestClient restClient = builder.build();

        Request request = new Request("GET", "/auto_keyword/_search/template");

        //request body
        /*
        {
            "id": "sample_0001",
            "params": {
              "keyword": "애터미"
            }
          }
        */

        Gson Agson = new Gson();

        Map<String, Object> map = new HashMap<>();
        map.put("id", "sample_0001");
        
        Map<String, Object> term_1 = new HashMap<>();
        term_1.put( "keyword" , "애터미" );

        Map<String, Object> term_2 = new HashMap<>();
        term_2.put("params", term_1);

        //두개 map 합치기
        map.putAll(term_2);

        //json string으로 변환
        String mapString = Agson.toJson(map);

        //requeyst body 에 작성한 mapping 싣기 
        request.setJsonEntity(mapString);


        try{

            Response response = restClient.performRequest(request);

            //반환값 httpEntity -> string 변환하여 전달 (바로 json 으로 바꿀경우 문제발생  -> " <- 이스케이프 문제   )
            String responseBody = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8")); 
            //System.out.println("response --> " + responseBody)


            clearScreen();
            //string -> json 으로 가져오기 
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(responseBody, JsonNode.class);

            //문자로 json  key 로 value 가져오기 
            JsonNode brandNode = jsonNode.get("took");
            String took = brandNode.asText();
            System.out.println("took = " + took);

            //hits -> hits
            JsonNode hits = jsonNode.get("hits");
            JsonNode childHits = hits.get("hits");
            int hitsNum = childHits.size();

            //return value
            /*
                "_source": {
                    "id": "238756802",
                    "name": "애터미 헤모힘*1set"
                },
                "fields": {
                    "id": [
                        "238756802"
                    ]
                },
                "highlight": {
                    "name.ngram": [
                        "<em>애터미 </em>헤모힘*1set"
                    ]
            */

            
            System.out.println("start : 08");
            System.out.println(" ");

            //반복되어야 하는 부분
            for (int i = 0; i <hitsNum; i++) {
              
              JsonNode childHit = childHits.get(i);//배열
              JsonNode _source = childHit.get("_source");

              JsonNode id = _source.get("id");
              System.out.println("id = " + id.asText());
              JsonNode name = _source.get("name");
              System.out.println("name = " + name.asText());
              

              JsonNode highlight = childHit.get("highlight");
              JsonNode name_ngram = highlight.get("name.ngram");
              for (JsonNode jsonNode2 : name_ngram) {
                  System.out.println("name.ngram = " + jsonNode2.asText());
              }

            }

            
            System.out.println(" ");
            System.out.println("end : 08");

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println( "error : "+ e.getMessage() );
        }



    }


    public static void clearScreen() {  
      System.out.print("\033[H\033[2J");  
      System.out.flush();  
    }  

    public static  String prettyPrinting(String str){

      String result = "";

      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      JsonParser jp = new JsonParser();
      JsonElement je = jp.parse(str);
      result = gson.toJson(je);

      return result;

    }


     

      
}
