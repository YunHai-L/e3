package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;


/**
 * @Auther: YunHai
 * @Date: 2018/12/12 18:14
 * @Description:
 */
public class TestSolrJ {

    /*@Test
    public void addDocument() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.130:8080/solr/");

        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id","test001");
        solrInputDocument.addField("item_title","中文名测试001");
        solrInputDocument.addField("item_price",1000);
        solrServer.add(solrInputDocument);
        solrServer.commit();

    }

    @Test
    public void deleteDocument() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.130:8080/solr/");
        solrServer.deleteByQuery("item_title:  中文名测试001");
        solrServer.commit();
    }


    @Test
    public void quieryIndex() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.130:8080/solr/");
        SolrQuery query = new SolrQuery();
        query.set("q","*:*");

        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList results = queryResponse.getResults();

        System.out.println("总数: " + results.getNumFound());
        for (SolrDocument doc : results){
            System.out.println(doc.get("item_title"));
            System.out.println(doc.get("item_sell_point"));
            System.out.println("=========================");
        }


    }


    @Test
    public void queryIndexFuza() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.130:8080/solr/");
        SolrQuery query = new SolrQuery();
        query.setQuery("手机");
        query.setStart(0);
        query.setRows(20);
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.addHighlightField("item_sell_point");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");

        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList documentList = queryResponse.getResults();
        System.out.println("总记录数: "+documentList.getNumFound());

        Map<String, Map<String, List<String>>> highMap = queryResponse.getHighlighting();
        for(SolrDocument doc : documentList){
//            第一层id   第二层域名
            List<String> highs = highMap.get(doc.get("id")).get("item_title");
            List<String> highs2 = highMap.get(doc.get("id")).get("item_sell_point");

            System.out.println("name: "+ doc.get("item_title"));
            System.out.println("point: "+ doc.get("item_sell_point"));
            if (highs != null && highs.size() > 0)
            System.out.println("highTitle: "+ highs.get(0));
            if (highs2 != null && highs2.size() > 0)
            System.out.println("highPoint: "+ highs2.get(0));


            System.out.println("=============================================================");
        }


//        query.setQuery();
    }

*/

}
