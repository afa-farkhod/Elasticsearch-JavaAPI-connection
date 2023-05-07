
package Nia;

import java.util.Collection;
import java.util.HashMap;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.graph.Connection;
import org.elasticsearch.client.graph.GraphExploreRequest;
import org.elasticsearch.client.graph.GraphExploreResponse;
import org.elasticsearch.client.graph.Hop;
import org.elasticsearch.client.graph.Vertex;
import org.elasticsearch.client.graph.VertexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class search_dmap0010 extends search
{
    String rtnVal = "";

    public String execute()
    {
        return rtnVal;
    }

    public void dmap0010(RestHighLevelClient client , 
                         String keyword ,String operator, int vertices_size, int connections_size)
    {

        try
        {

            Operator selectOperator = Operator.OR;
            if (operator.equals("and")==true)
            {
                selectOperator = Operator.AND;
            }
            else    
            {
                selectOperator = Operator.OR;
            }
            
            //************************************************************** */

            GraphExploreRequest request = new GraphExploreRequest();
            //request.indices("dmap_bks_mgr_news_tbl"); //test DB
            request.indices("dmap_map_mgr_news_tbl"); //real DB

            request.useSignificance(true);
            request.sampleSize(2000);
            TimeValue tv = new TimeValue(5000);
            request.timeout(tv);

            QueryStringQueryBuilder queryBuilder1 = QueryBuilders.queryStringQuery(keyword).defaultOperator(selectOperator);
            
            request.createNextHop(queryBuilder1).addVertexRequest("feat").size(vertices_size).minDocCount(3); //vertices
            request.createNextHop(null).addVertexRequest("feat").size(connections_size).minDocCount(3); //connections
            
            GraphExploreResponse exploreResponse = client.graph().explore(request, RequestOptions.DEFAULT); 

            //System.out.println(queryBuilder1.toString());
            //System.out.println(request.getHop(0).guidingQuery());
            //System.out.println(client.graph().explore(request, RequestOptions.DEFAULT).toString());

            //respons to json 
            //XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
            //System.out.println("requestBody: " + Strings.toString(request.toXContent(xContentBuilder, ToXContent.EMPTY_PARAMS)));

            Collection<Vertex> v = exploreResponse.getVertices();
            Collection<Connection> c = exploreResponse.getConnections();


            JSONArray resultVertex = new JSONArray();
            for (Vertex vertex : v) {
                
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("term", vertex.getTerm());
                elements.put("weight", vertex.getWeight());
                elements.put("depth", vertex.getHopDepth());
                JSONObject data = new JSONObject(elements);
                resultVertex.add(data);
            }

            JSONArray resultConnection = new JSONArray();
            for (Connection link : c) {

                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("source", link.getFrom().getTerm());
                elements.put("target", link.getTo().getTerm());
                elements.put("weight", link.getWeight());
                elements.put("doc_count", link.getDocCount());
                JSONObject data = new JSONObject(elements);
                resultConnection.add(data);
            }

            //front
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode", "OK");
            result.put("resultCnt", resultVertex.size());   
            result.put("resultMsg", resultVertex);
            //result.put("resultConnections", resultConnection);
            //result.put("resultConnectionsCnt", resultConnection.size());
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