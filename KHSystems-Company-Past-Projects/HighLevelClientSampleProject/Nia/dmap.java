package NIA.NiaElastic.src.main.java.Nia;

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

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class dmap {

    public static void main(String[] args) {
        try {

            Nia.dmap app = new Nia.dmap();
            // test server
            RestHighLevelClient client = app.connectToES("192.168.12.214", "elastic", "elastic", 9200);

            if (client == null) {
                // 에러
            }

            String rtnVal = "";
            String[] excludeFields = { "" };

            if (rtnVal.equals("client is null. Try connection to connectToES()")) {
                app.connectToES("172.18.69.149", "elastic", "elastic", 9200);
            }

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(rtnVal);
            String code = object.get("resultCode").toString();

            if (code.equals("ERROR")) {
                System.out.println(rtnVal);
            } else {
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

    // 연결
    public RestHighLevelClient connectToES(String ip, String id, String pwd, int port) {

        try {

            RestHighLevelClient client;
            RestClientBuilder clientBuilder;

            // 자격증명
            final CredentialsProvider credentialsprovider = new BasicCredentialsProvider();
            credentialsprovider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

            final int maxConnectTotal = 400;
            final int maxConnectPerRoute = 50;

            clientBuilder = RestClient.builder(new HttpHost(ip, port));

            // sniifer
            // clientBuilder.setFailureListener(sniffOnFailureListener);

            clientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsprovider);
                    httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                    httpClientBuilder.setMaxConnTotal(maxConnectTotal);

                    return httpClientBuilder;
                }
            });

            client = new RestHighLevelClient(clientBuilder);

            // sniffer
            // Sniffer sniffer = Sniffer.builder(client.getLowLevelClient())
            // .setSniffAfterFailureDelayMillis(30000)
            // .build();
            // sniffOnFailureListener.setSniffer(sniffer);

            return client;

        } catch (Exception e) {
            return null;
        }

    }

    public RestHighLevelClient connectToES(String ip1, int port1, String ip2, int port2, String ip3, int port3,
            String id, String pwd) {

        try {

            RestHighLevelClient client;
            // SearchSourceBuilder ssb ;

            RestClientBuilder clientBuilder;
            final CredentialsProvider credentialsprovider = new BasicCredentialsProvider();
            credentialsprovider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

            final int maxConnectTotal = 400;
            final int maxConnectPerRoute = 50;

            // 접속 확인
            // SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
            // RestClient restClient = RestClient.builder(new HttpHost(ip, port))
            // .setFailureListener(sniffOnFailureListener)
            // .build();
            // Sniffer sniffer = Sniffer.builder(restClient)
            // .setSniffAfterFailureDelayMillis(30000)
            // .build();
            // sniffOnFailureListener.setSniffer(sniffer);

            // restClient.close();

            // 엘라스틱 접속 주소
            clientBuilder = RestClient.builder(new HttpHost(ip1, port1), new HttpHost(ip2, port2),
                    new HttpHost(ip3, port3));
            // clientBuilder.setFailureListener(sniffOnFailureListener);

            clientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsprovider);
                    httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                    httpClientBuilder.setMaxConnTotal(maxConnectTotal);

                    return httpClientBuilder;
                }
            });

            client = new RestHighLevelClient(clientBuilder);

            // Sniffer sniffer = Sniffer.builder(client.getLowLevelClient())
            // .setSniffAfterFailureDelayMillis(30000)
            // .build();
            // sniffOnFailureListener.setSniffer(sniffer);

            return client;

        } catch (Exception e) {
            return null;
        }

    }

    public String call_dmap0001(RestHighLevelClient client, int page, int size, String[] excludeFields, String dataSe) {
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        search_dmap0001_ST s = new search_dmap0001_ST();
        s.search(client, ssb, page, size, excludeFields, dataSe);
        return s.execute();
    }

    public String call_dmap0002(RestHighLevelClient client, int page, int size, String[] excludeFields,
            String brmCls1Nm, String brmCls2Nm, String dataSe) {
        search_dmap0002_ST s = new search_dmap0002_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, brmCls1Nm, brmCls2Nm, dataSe);
        return s.execute();
    }

    public String call_dmap0003(RestHighLevelClient client, int page, int size, String[] excludeFields,
            String keyword) {
        search_dmap0003_ST s = new search_dmap0003_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, keyword);
        return s.execute();
    }

    public String call_dmap0004(RestHighLevelClient client, int page, int size, String[] excludeFields, String keyword,
            String operator, String brmCls1Nm, String orgNm, String openDataYn) {
        search_dmap0004_ST s = new search_dmap0004_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, keyword, operator, brmCls1Nm, orgNm, openDataYn);
        return s.execute();
    }

    public String call_dmap0005(RestHighLevelClient client, int page, int size, String[] excludeFields, String docId,
            String openDataYn) {

        search_dmap0005_ST s = new search_dmap0005_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, docId, openDataYn);
        return s.execute();
    }

    public String call_dmap0006(RestHighLevelClient client, int page, int size, String[] excludeFields, String[] docId,
            String openHoldColList) {

        search_dmap0006_ST s = new search_dmap0006_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, docId, openHoldColList);
        return s.execute();
    }

    public String call_dmap0007(RestHighLevelClient client, int page, int size, String[] excludeFields, String[] docId,
            int aggsSize) {

        search_dmap0007_ST s = new search_dmap0007_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, docId, aggsSize);
        return s.execute();
    }

    public String call_dmap0008(RestHighLevelClient client, int page, int size, String[] excludeFields, String keyword,
            String operator, int aggsSize, String openDataYn) {

        search_dmap0008_ST s = new search_dmap0008_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, keyword, operator, aggsSize, openDataYn);
        return s.execute();
    }

    public String call_dmap0009(RestHighLevelClient client, int page, int size, String[] excludeFields,
            String sortField, String sortDesc, String operator, String keyword) {

        search_dmap0009_ST s = new search_dmap0009_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFields, sortField, sortDesc, operator, keyword);
        return s.execute();
    }

    public String call_dmap0010(RestHighLevelClient client, String keyword, String operator, int vertices_size,
            int connections_size) {

        search_dmap0010 s = new search_dmap0010();
        s.dmap0010(client, keyword, operator, vertices_size, connections_size);
        return s.execute();
    }

    public String call_dmap0011(RestHighLevelClient client, String keyword, String operator, int vertices_size,
            int connections_size) {

        search_dmap0011 s = new search_dmap0011();
        s.dmap0011(client, keyword, operator, vertices_size, connections_size);
        return s.execute();
    }

    public String call_dmap0012(RestHighLevelClient client, String keyword, String Operator, Integer size) {
        search_dmap0012_ST s = new search_dmap0012_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, keyword, Operator, size);
        return s.execute();
    }

    public String call_dmap0013(RestHighLevelClient client, int page, int size, String fromDate, String toDate,
            int aggSize) {
        search_dmap0013_ST s = new search_dmap0013_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, fromDate, toDate, aggSize);
        return s.execute();
    }

    public String call_dmap0014(RestHighLevelClient client, int page, int size, String text) {

        search_dmap0014_ST s = new search_dmap0014_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, text);
        return s.execute();
    }

    public String call_dmap0015(RestHighLevelClient client, int page, int size, String[] excludeFilds, String keyword,
            String operator, String brmCls1Nm, String orgNm, String[] openScopeOrgCd) {

        search_dmap0015_ST s = new search_dmap0015_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFilds, keyword, operator, brmCls1Nm, orgNm, openScopeOrgCd);
        return s.execute();
    }

    public String call_dmap0016(RestHighLevelClient client, int page, int size, String[] excludeFilds,
            String mtaTblId) {
        search_dmap0016_ST s = new search_dmap0016_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFilds, mtaTblId);
        return s.execute();
    }

    public String call_dmap0016_col(RestHighLevelClient client, int page, int size, String[] excludeFilds,
            String mtaTblId, String prsnInfoYn, String encTrgYn, String openDataYn) {
        search_dmap0016_col_ST s = new search_dmap0016_col_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFilds, mtaTblId, prsnInfoYn, encTrgYn, openDataYn);
        return s.execute();
    }

    public String call_dmap0017_col(RestHighLevelClient client, int page, int size, String[] excludeFilds,
            String[] mtaTblId, String holdColList) {
        search_dmap0017_ST s = new search_dmap0017_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFilds, mtaTblId, holdColList);
        return s.execute();
    }

    public String call_dmap0018(RestHighLevelClient client, int page, int size, String[] excludeFilds,
            String[] mtaTblId, int aggsSize) {
        search_dmap0018_ST s = new search_dmap0018_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFilds, mtaTblId, aggsSize);
        return s.execute();
    }

    public String call_dmap0019(RestHighLevelClient client, int page, int size, String[] excludeFilds, String keyword,
            String operator, int aggsSize) {
        search_dmap0019_ST s = new search_dmap0019_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, excludeFilds, keyword, operator, aggsSize);
        return s.execute();
    }

    public String call_dmap0020(RestHighLevelClient client, int page, int size, String text) {

        search_dmap0020_ST s = new search_dmap0020_ST();
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        s.search(client, ssb, page, size, text);
        return s.execute();
    }

}
