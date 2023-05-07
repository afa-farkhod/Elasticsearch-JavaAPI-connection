
package Nia;

//인터페이스 같은..
public class search
{
    public boolean parseArgs(String Args)
    {
//         sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); 
// sourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));  
        // 관련된 전 처리
        return true;
    }

    public void prebuildQuery()
    {
        // Sort, Filter, Source 선택
    }

    public void parseResult()
    {
        // 결과에 대해서 파싱

    }

    public void mainquery()
    {
        //오버라이드 
    }

    public String execute()
    {
        //json으로 출력
        //공통 출력 부분 추가
       // parseArgs();
       String rtn = "";
       return rtn;
       
    }
}




