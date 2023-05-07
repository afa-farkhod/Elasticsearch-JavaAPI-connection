
package Nia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class testConn
{

    public static void main( String[] args )
    {
        try {

            Nia.dmap app = new Nia.dmap();
            RestHighLevelClient client = app.connectToES("192.168.12.214","elastic", "elastic", 9200);
            
            if (client == null) 
            {
                //에러
            }

            ClusterHealthRequest request = new ClusterHealthRequest();
            ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
            //client.cluster().healthAsync(request, RequestOptions.DEFAULT, listener); 
            //String clusterName = response.getClusterName(); 
            ClusterHealthStatus status = response.getStatus(); 

            System.out.println(status.toString()); //GREEN, YELLOW , RED 

            client.close();

        } catch (Exception e) {
            System.out.println(e.getMessage()); 
        }
        
            
    }

    
    
    //연결 
    public RestHighLevelClient connectToES(String ip, String id, String pwd, int port  )
    {

        try {

            RestHighLevelClient client;
            RestClientBuilder clientBuilder;

            //자격증명
            final CredentialsProvider credentialsprovider = new BasicCredentialsProvider();
            credentialsprovider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));
            
            //connection , port Max
            final int maxConnectTotal = 400;
            final int maxConnectPerRoute = 50;
            
            //에러확인용 
            //SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();

            clientBuilder = RestClient.builder(new HttpHost(ip, port)); 

            //sniifer
            //clientBuilder.setFailureListener(sniffOnFailureListener); 
            
            clientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() 
            {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) 
                {
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsprovider);
                    httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                    httpClientBuilder.setMaxConnTotal(maxConnectTotal);
                    
                    return httpClientBuilder;
                }
            }
            );
            
            client = new RestHighLevelClient(clientBuilder);
            
            //sniffer
            // Sniffer sniffer = Sniffer.builder(client.getLowLevelClient())
            //                 .setSniffAfterFailureDelayMillis(30000) 
            //                 .build();
            // sniffOnFailureListener.setSniffer(sniffer); 

            return client;

        } catch (Exception e) {
            return null;
        }
        
    }


    public RestHighLevelClient connectToES(String ip1, int port1, String ip2, int port2, String ip3, int port3 , String id, String pwd  )
    {

        try {

            RestHighLevelClient client;
            //SearchSourceBuilder ssb ; 
            
            RestClientBuilder clientBuilder;
            final CredentialsProvider credentialsprovider = new BasicCredentialsProvider();
            credentialsprovider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));
            
            final int maxConnectTotal = 400;
            final int maxConnectPerRoute = 50;
            
            //접속 확인
           // SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
            // RestClient restClient = RestClient.builder(new HttpHost(ip, port))
            //                                     .setFailureListener(sniffOnFailureListener) 
            //                                     .build();
            // Sniffer sniffer = Sniffer.builder(restClient)
            //                 .setSniffAfterFailureDelayMillis(30000) 
            //                 .build();
            // sniffOnFailureListener.setSniffer(sniffer); 

            //restClient.close();


            //엘라스틱 접속 주소  
            clientBuilder = RestClient.builder(new HttpHost(ip1, port1),new HttpHost(ip2, port2),new HttpHost(ip3, port3));
            //clientBuilder.setFailureListener(sniffOnFailureListener); 
            
            clientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() 
            {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) 
                {
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsprovider);
                    httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                    httpClientBuilder.setMaxConnTotal(maxConnectTotal);
                    
                    return httpClientBuilder;
                }
            }
            );
            
            client = new RestHighLevelClient(clientBuilder);
            

            // Sniffer sniffer = Sniffer.builder(client.getLowLevelClient())
            //                 .setSniffAfterFailureDelayMillis(30000) 
            //                 .build();
            // sniffOnFailureListener.setSniffer(sniffer); 

            return client;

        } catch (Exception e) {
            return null;
        }
        
        
    }




}

