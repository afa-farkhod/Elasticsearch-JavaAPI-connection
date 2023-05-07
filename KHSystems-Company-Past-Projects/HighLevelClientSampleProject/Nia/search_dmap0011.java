
package Nia;

import java.util.Collection;
import java.util.HashMap;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.graph.GraphExploreRequest;
import org.elasticsearch.client.graph.GraphExploreResponse;
import org.elasticsearch.client.graph.Vertex;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class search_dmap0011 extends search
{
    String rtnVal = "";

    public String execute()
    {
        return rtnVal;
    }

    public void dmap0011(RestHighLevelClient client , 
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
            

            GraphExploreRequest request = new GraphExploreRequest();
            //request.indices("dmap_bks_mgr_law_tbl");
            request.indices("dmap_map_mgr_law_tbl"); //real DB
            request.useSignificance(true);
            request.sampleSize(2000);
            TimeValue tv = new TimeValue(5000);
            request.timeout(tv);

            QueryStringQueryBuilder queryBuilder1 = QueryBuilders.queryStringQuery(keyword).defaultOperator(selectOperator);
            
            request.createNextHop(queryBuilder1).addVertexRequest("cont").size(vertices_size).minDocCount(3);
            request.createNextHop(null).addVertexRequest("cont").size(connections_size).minDocCount(3);
            
            GraphExploreResponse exploreResponse = client.graph().explore(request, RequestOptions.DEFAULT); 
            //System.out.println(queryBuilder1.toString());
            //System.out.println(request.getHop(0).guidingQuery());
            

            //결과값
            Collection<Vertex> v = exploreResponse.getVertices();
            //Collection<Connection> c = exploreResponse.getConnections();

            JSONArray resultVertex = new JSONArray();
            for (Vertex vertex : v) {
                //System.out.println(vertex.getField() + ":" + vertex.getTerm() + " discovered at hop depth " + vertex.getHopDepth());
                HashMap<String, Object> elements = new HashMap<String, Object>();
                elements.put("term", vertex.getTerm());
                elements.put("weight", vertex.getWeight());
                JSONObject data = new JSONObject(elements);
                resultVertex.add(data);
            }

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("resultCode", "OK");
            result.put("resultCnt", resultVertex.size());   
            result.put("resultMsg", resultVertex);
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