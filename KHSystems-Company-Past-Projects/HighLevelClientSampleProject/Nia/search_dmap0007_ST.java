
package Nia;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class search_dmap0007_ST extends search
{
    String rtnVal = "";

    public String execute()
    {
        return rtnVal;
    }

    public void search(RestHighLevelClient client , SearchSourceBuilder ssb, int page , int size ,String[] excludeFields
                    , String[] docId,int aggs_size)
    {
        try
        {
            //전처리

            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest("dmap_map_nation_datamap_open"));//index

            request.setScriptType(ScriptType.STORED);
            request.setScript("dmap_0007");//script title

            Map<String, Object> params = new HashMap<>();
            if (page!=0) params.put("page", page);
            if (size!=0) params.put("size", size);
            params.put("excludeFields", excludeFields);


            params.put("docId", docId);
            if (aggs_size!=0) params.put("aggs_size", aggs_size);
            request.setScriptParams(params);

            /*쿼리 보기*/ 
            // XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
            // System.out.println("requestBody: " + Strings.toString(request.toXContent(xContentBuilder, ToXContent.EMPTY_PARAMS)));

            SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
            SearchResponse searchResponse = response.getResponse();

            /* 랜더링 된 쿼리 보기 */
            // request.setSimulate(true); 
            // BytesReference source = response.getSource(); 
            // System.out.println("requestBody: " + source.utf8ToString());
            
            //hits 검색
            SearchHits hits = searchResponse.getHits();

            //집계
            JSONArray resultAgg1 = new JSONArray();
            Terms buck1 = searchResponse.getAggregations().get("open_hold_col_list");
            for (Terms.Bucket entry : buck1.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAgg1.add(data);
            }
            

            //result
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode", "OK");
            result.put("resultCnt", hits.getTotalHits().value );   
            result.put("resultAggCnt", resultAgg1.size() );   
            //result.put("resultMsg", req_array);
            result.put("resultAggsMsg", resultAgg1);

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
        
    }


}