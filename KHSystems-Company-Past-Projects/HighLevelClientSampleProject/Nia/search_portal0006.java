
package Nia;

import java.util.HashMap;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class search_portal0006 extends search
{
    String rtnVal = "";

    public String execute()
    {
        return rtnVal;
    }

    public void search(RestHighLevelClient client , SearchSourceBuilder ssb, String[] dType)
    {

        try
        {

            //쿼리
            BoolQueryBuilder bQuery = new BoolQueryBuilder();
            bQuery.filter(QueryBuilders.termsQuery("dType", dType));
            ssb.query(bQuery);

            //count request
            CountRequest countRequest = new CountRequest("portal_public_data"); 
            countRequest.source(ssb); 

            //count response
            CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);

            long count = countResponse.getCount();
            // RestStatus status = countResponse.status();
            // Boolean terminatedEarly = countResponse.isTerminatedEarly();


            
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode", "OK");
            result.put("resultCnt", count);
            JSONObject jsonObject = new JSONObject(result);
            rtnVal = jsonObject.toString();

        }

        catch (Exception e) {

            HashMap<String, Object> err = new HashMap<String, Object>();
            err.put("resultCode", "ERROR");
            err.put("resultCnt", 0);
            err.put("resultMsg", e.getMessage());
            JSONObject errObj = new JSONObject(err);
            rtnVal = errObj.toString();

        } 
        finally {

        }
        
    }


}