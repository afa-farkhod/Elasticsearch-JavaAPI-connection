package Nia;

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

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class potal
{
    //접속 연결 안되었음 부분 추가 

    public static void main( String[] args )
    {
        try {

            Nia.potal app = new Nia.potal();

            RestHighLevelClient client = app.connectToES("192.168.12.214","elastic", "elastic", 9200);
            if (client == null) 
            {
                //에러처리
            }

            String[] excludeFields = {};
            
            String rtnVal = "";

            //rtnVal = app.call_portal0008(client, "15059462", 0, 0 );

            //rtnVal = app.call_portal0005(client, 0, 0, "korea" );

            // String[] dType = {"API","FILE"};
            // String[] brm  = {}; 
            // String[] svcType = {"REST","SOAP","LINK"};
            // String[] instt = {};
            // String[] kwrdArray = {};
            // String[] extsn = {"CSV","XML","JSON"};
            // String[] coreDataNmArray = {};
            // String[] org = {"Korea Employment Information Service","Ministry of Maritime Affairs and Fisheries","Kangwon-do"};
            // String[] mustNot = {};

            // rtnVal = app.call_portal0004(client, 0, 10, excludeFields , ""/*sort*/, "" /*sort_order*/,
            //     dType, brm, svcType, instt, kwrdArray, extsn , coreDataNmArray, org,
            //     "information", mustNot, 20);


            //rtnVal = app.call_portal0003(client, 0, 30, excludeFields );


            //String[] dType = {"API","FILE","STD"};
            //rtnVal = app.call_portal0006(client, dType );
            //rtnVal = app.call_portal0002(client, 0, 0, "평생" );
            rtnVal = app.call_portal0002(client, 0, 0, "vudtod" );

            // String[] dType = {"API","FILE"};
            // String[] brm  = {"국토관리","사회복지"}; 
            // String[] svcType = {"REST","다운로드"};
            // String[] instt = {"국가행정기관","자치행정기관"};
            // String[] kwrdArray = {"건물"};
            // String[] extsn = {"CSV","XML"};
            // String[] coreDataNmArray = {"건축정보"};
            // String[] org = {"국토교통부"};
            // String[] mustNot = {};
            // String[] publicDataPk = {"3073145","15029119","15018758"};

            // String[] dType = {"API","FILE", "STD"};
            // String[] brm  = {}; 
            // String[] svcType = {};
            // String[] instt = {};
            // String[] kwrdArray = {"대기환경"};
            // String[] extsn = {};
            // String[] coreDataNmArray = {};
            // String[] org = {};
            // String[] mustNot = {};
            // String[] publicDataPk = {};

            // rtnVal = app.call_portal0001(client, 0, 10, excludeFields , ""/*sort*/, "" /*sort_order*/,
            //     dType, brm, svcType, instt, kwrdArray, extsn , coreDataNmArray, org, publicDataPk,
            //     "미세먼지", mustNot, 20);

            if (rtnVal.equals("client is null. Try connection to connectToES()"))
            {
                //재접속
                app.connectToES("172.18.69.149","elastic", "elastic", 9200);
            }    
            
          //*****************/

            JSONParser parser = new JSONParser(); 
            JSONObject object = (JSONObject)parser.parse(rtnVal); 
            String code = object.get("resultCode").toString();

            if (code.equals("ERROR"))
            {
                System.out.println(rtnVal); 
            }
            else    
            {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(rtnVal);
                String prettyJsonString = gson.toJson(je);    
                
                System.out.println(prettyJsonString); 
            }

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
            //SearchSourceBuilder ssb ; 
            
            RestClientBuilder clientBuilder;
            final CredentialsProvider credentialsprovider = new BasicCredentialsProvider();
            credentialsprovider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));
            
            final int maxConnectTotal = 400;
            final int maxConnectPerRoute = 50;
            
            //접속 확인
            //SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
            //엘라스틱 접속 주소  
            
            clientBuilder = RestClient.builder(new HttpHost(ip, port)); 
            
            //sniffer
            //clientBuilder.setFailureListener(sniffOnFailureListener); 

            //3ip conn
            //clientBuilder = RestClient.builder(new HttpHost(ip1, port1),new HttpHost(ip2, port2),new HttpHost(ip3, port3)); 
            
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
            
            //sniffer Listener
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


    public String call_portal0001(RestHighLevelClient client , 
                                int page, int size, String[] excludeFields , String sort, String sort_order,
                                String[] dType, String[] brm, String[] svcType, String[] instt, String[] kwrdArray, String[] extsn , String[] coreDataNmArray, String[]  org,String[] PublicDataPk,
                                String keyword, String[] must_not, int aggs_size )
    {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_portal0001_ST s = new search_portal0001_ST();
        s.search(client ,ssb ,
                page, size, excludeFields , sort, sort_order,
                dType, brm, svcType, instt, kwrdArray, extsn , coreDataNmArray, org, PublicDataPk,
                keyword,must_not, aggs_size );
        

        return s.execute();
    }

    public String call_portal0002(RestHighLevelClient client, int page, int size, String keyword)
    {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_portal0002_ST s = new search_portal0002_ST();
        s.search(client ,ssb , page, size, keyword );
        return s.execute();
    }

    public String call_portal0003(RestHighLevelClient client, int page, int size, String[] excludeFields)
    {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_portal0003_ST s = new search_portal0003_ST();
        s.search(client ,ssb , page, size, excludeFields);
        return s.execute();
    }


    public String call_portal0004(RestHighLevelClient client , 
                                int page, int size, String[] excludeFields , String sort, String sort_order,
                                String[] dType, String[] brm, String[] svcType, String[] instt, String[] kwrdArray, String[] extsn , String[] coreDataNmArray, String[]  org,
                                String keyword, String[] must_not, int aggs_size )
    {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_portal0004_ST s = new search_portal0004_ST();
        s.search(client ,ssb ,
                page, size, excludeFields , sort, sort_order,
                dType, brm, svcType, instt, kwrdArray, extsn , coreDataNmArray, org,
                keyword,must_not, aggs_size );
        

        return s.execute();
    }

    public String call_portal0005(RestHighLevelClient client, int page, int size, String keyword)
    {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_portal0005_ST s = new search_portal0005_ST();
        s.search(client ,ssb , page, size, keyword );
        return s.execute();
    }

    
    public String call_portal0006(RestHighLevelClient client, String[] dType)
    {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_portal0006 s = new search_portal0006();
        s.search(client ,ssb , dType );
        return s.execute();
    }

    public String call_portal0008(RestHighLevelClient client , String keyword , int size, int aggsSize)
    {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_portal0008_ST s = new search_portal0008_ST();
        s.search(client ,ssb , keyword , size, aggsSize );
        return s.execute();
    }

    

}

