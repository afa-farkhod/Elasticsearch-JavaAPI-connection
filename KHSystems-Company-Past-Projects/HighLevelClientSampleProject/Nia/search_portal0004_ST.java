
package Nia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class search_portal0004_ST extends search
{
    String rtnVal = "";

    public String execute()
    {
        return rtnVal;
    }

    public void search(RestHighLevelClient client , SearchSourceBuilder ssb,
                        int page, int size, String[] excludeFields , String sort, String sort_order,
                        String[] dType, String[] brm, String[] svcType, String[] insttEn, String[] kwrdArrayEn, String[] extsn , String[] coreDataNmArray, String[]  org,
                        String keyword, String[] must_not, int aggs_size )
    {
        try
        {
            //전처리

            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest("portal_public_data_en"));//index

            request.setScriptType(ScriptType.STORED);
            request.setScript("portal_0004");//script title

            Map<String, Object> params = new HashMap<>();
            if (page != 0) params.put("page", page);
            if (size != 0) params.put("size", size);
            if(aggs_size != 0 ) params.put("aggs_size", aggs_size);

            params.put("excludeFields", excludeFields);
            params.put("sort", sort);
            params.put("sort_order", sort_order);
            params.put("must_not", must_not);

            if(keyword.equals("") || keyword.isEmpty()==true )
                 params.put("keyword", "*");
            else
                params.put("keyword", keyword);

            //dType 필수
            Map<String, Object> attrDType = new HashMap<>();
            Map<String, Object> termsDType = new HashMap<>();
            attrDType.put("dType", dType);
            termsDType.put("terms", attrDType);

            List<Object> empty = new ArrayList<Object>();
            empty.add(termsDType);

            if (insttEn.length !=0 )
            {
                Map<String, Object> attrInstt = new HashMap<>();
                Map<String, Object> termsInstt = new HashMap<>();
                attrInstt.put("insttEn", insttEn);
                termsInstt.put("terms", attrInstt);
                empty.add(termsInstt);
            } 

            if (brm.length !=0)
            {
                Map<String, Object> attrBrm = new HashMap<>();
                Map<String, Object> termsBrm = new HashMap<>();
                attrBrm.put("brmEn", brm);
                termsBrm.put("terms", attrBrm);
                empty.add(termsBrm);
            } 

            if (kwrdArrayEn.length !=0)
            {
                Map<String, Object> attrKwrdArray = new HashMap<>();
                Map<String, Object> termsKwrdArray = new HashMap<>();
                attrKwrdArray.put("kwrdEnArray", kwrdArrayEn);
                termsKwrdArray.put("terms", attrKwrdArray);
                empty.add(termsKwrdArray);
            } 

            if (extsn.length !=0){
                Map<String, Object> attrExtsn = new HashMap<>();
                Map<String, Object> termsExtsn = new HashMap<>();
                attrExtsn.put("extsn", extsn);
                termsExtsn.put("terms", attrExtsn);
                empty.add(termsExtsn);
            } 

            if (svcType.length !=0){
                Map<String, Object> attrSvcType = new HashMap<>();
                Map<String, Object> termsSvcType = new HashMap<>();
                attrSvcType.put("svcTypeEn", svcType);
                termsSvcType.put("terms", attrSvcType);
                empty.add(termsSvcType);
            } 

            if (coreDataNmArray.length !=0){
                Map<String, Object> attrCoreDataNmArray = new HashMap<>();
                Map<String, Object> termsCoreDataNmArray = new HashMap<>();
                attrCoreDataNmArray.put("coreDataNmEnArray", coreDataNmArray);
                termsCoreDataNmArray.put("terms", attrCoreDataNmArray);
                empty.add(termsCoreDataNmArray);
            } 

            if (org.length !=0){
                Map<String, Object> attrOrg = new HashMap<>();
                Map<String, Object> termsOrg = new HashMap<>();
                attrOrg.put("orgEn", org);
                termsOrg.put("terms", attrOrg);
                empty.add(termsOrg);
            } 

            params.put("filter", empty);

            request.setScriptParams(params);
         
            /*쿼리 보기*/ 
             XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
             System.out.println("requestBody: " + Strings.toString(request.toXContent(xContentBuilder, ToXContent.EMPTY_PARAMS)));

            //소스확인 (setSimulate= true)
            //랜더링하면 값이 안나옴
            // request.setSimulate(true); //랜더된 소스보기 
            // SearchTemplateResponse renderResponse = client.searchTemplate(request, RequestOptions.DEFAULT);
            // BytesReference source = renderResponse.getSource(); 
            // System.out.println("requestBody: " + source.utf8ToString());

            SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
            SearchResponse searchResponse = response.getResponse();
            
            //hits 검색
            SearchHits hits = searchResponse.getHits();
            JSONArray req_array = new JSONArray();
            for(SearchHit hit : hits)
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.putAll(hit.getSourceAsMap());
                elements.put("_score", hit.getScore());
                JSONObject data = new JSONObject(elements);
                req_array.add(data);
            }

            //svcType
            Terms bucksvcType = searchResponse.getAggregations().get("svcTypeEn");
            JSONArray resultAggsvcType = new JSONArray();
            for (Terms.Bucket entry : bucksvcType.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAggsvcType.add(data);
            }
            //instt
            Terms buckinstt = searchResponse.getAggregations().get("insttEn");
            JSONArray resultAgginstt = new JSONArray();
            for (Terms.Bucket entry : buckinstt.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAgginstt.add(data);
            }
            //coreDataNm
            Terms buckcoreDataNm = searchResponse.getAggregations().get("coreDataNmEn");
            JSONArray resultAggcoreDataNm = new JSONArray();
            for (Terms.Bucket entry : buckcoreDataNm.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAggcoreDataNm.add(data);
            }
            //extsn
            Terms buckextsn = searchResponse.getAggregations().get("extsn");
            JSONArray resultAggextsn = new JSONArray();
            for (Terms.Bucket entry : buckextsn.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAggextsn.add(data);
            }
            //org
            Terms buckOrg = searchResponse.getAggregations().get("orgEn");
            JSONArray resultAggOrg = new JSONArray();
            for (Terms.Bucket entry : buckOrg.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAggOrg.add(data);
            }
            //brm
            Terms buckbrm = searchResponse.getAggregations().get("brmEn");
            JSONArray resultAggbrm = new JSONArray();
            for (Terms.Bucket entry : buckbrm.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAggbrm.add(data);
            }
            //KwrdArray
            Terms buckKwrdArray = searchResponse.getAggregations().get("kwrdEn");
            JSONArray resultAggKwrdArray = new JSONArray();
            for (Terms.Bucket entry : buckKwrdArray.getBuckets()) 
            {
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("key", entry.getKey());
                elements.put("doc_count", entry.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultAggKwrdArray.add(data);
            }

            //front
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode", "OK");
            
            //hits
            result.put("resultCnt", hits.getTotalHits().value);   
            result.put("resultMsg", req_array);

            //KwrdArray
            result.put("resultKwrdArrayCnt", resultAggKwrdArray.size());   
            result.put("resultKwrdArrayMsg", resultAggKwrdArray);

            //org
            result.put("resultOrgCnt", resultAggOrg.size());   
            result.put("resultOrgMsg", resultAggOrg);
            //instt
            result.put("resultInsttCnt", resultAgginstt.size());   
            result.put("resultInsttMsg", resultAgginstt);
            //svcType
            result.put("resultSvcTypeCnt", resultAggsvcType.size());   
            result.put("resultSvcTypeMsg", resultAggsvcType);
            //extsn
            result.put("resultExtsnCnt", resultAggextsn.size());   
            result.put("resultExtsnMsg", resultAggextsn);
            //coreDataNm
            result.put("resultCoreDataNmCnt", resultAggcoreDataNm.size());   
            result.put("resultCoreDataNmMsg", resultAggcoreDataNm);
            //brm
            result.put("resultBrmCnt", resultAggbrm.size());   
            result.put("resultBrmMsg", resultAggbrm);

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