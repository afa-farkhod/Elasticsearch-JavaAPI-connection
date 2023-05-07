
package Nia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class search_dmap0020_ST extends search
{
    String rtnVal = "";

    public String execute()
    {
        return rtnVal;
    }

    public void search(RestHighLevelClient client , SearchSourceBuilder ssb, int page , int size ,String keyword)
    {
        try
        {
            //전처리

            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest("dmap_map_nation_datamap"));//index

            request.setScriptType(ScriptType.STORED);
            request.setScript("dmap_0020");//script title

            Map<String, Object> params = new HashMap<>();
            if (page!=0) params.put("page", page);
            if (size!=0) params.put("size", size);
            params.put("keyword", keyword);
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

            SearchHits hits = searchResponse.getHits();       

            //추천
            JSONArray reqOptions = new JSONArray();
            Suggest suggestResponse = searchResponse.getSuggest();

            CompletionSuggestion suggestion = (CompletionSuggestion) suggestResponse.getSuggestion("complete");
            List<CompletionSuggestion.Entry> entries = suggestion.getEntries();
            for (CompletionSuggestion.Entry entry : entries)
            {
                for (CompletionSuggestion.Entry.Option option : entry.getOptions())
                {
                    HashMap<String, Object> elements1 = new HashMap<String, Object>();
                    elements1.put("text", option.getText().toString());
                    JSONObject data1 = new JSONObject(elements1);
                    reqOptions.add(data1);
                }
            }


            JSONArray reqOptions2 = new JSONArray();
            CompletionSuggestion suggestion2 = (CompletionSuggestion) suggestResponse.getSuggestion("complete_eng");
            List<CompletionSuggestion.Entry> entries2 = suggestion2.getEntries();

            for (CompletionSuggestion.Entry entry : entries2)
            {
                for (CompletionSuggestion.Entry.Option option : entry.getOptions())
                {
                    HashMap<String, Object> elements1 = new HashMap<String, Object>();
                    elements1.put("text", option.getText().toString());
                    System.out.println(option.getText());
                    JSONObject data1 = new JSONObject(elements1);
                    reqOptions2.add(data1);
                }
            }

            //front
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode", "OK");
            result.put("resultCnt", hits.getTotalHits().value);   
            result.put("resultSuggCnt", reqOptions.size() );   
            result.put("resultSuggMsg", reqOptions);
            result.put("resultSuggEngCnt", reqOptions2.size() );   
            result.put("resultSuggEngMsg", reqOptions2);

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