package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @Auther: YunHai
 * @Date: 2018/12/15 19:26
 * @Description:
 */
public class TestSolrCloud {

/*
    @Test
    public void testAddDpcument() throws Exception{
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");

        solrServer.setDefaultCollection("collection2");

        SolrInputDocument document = new SolrInputDocument();
        document.setField("id","test008");
        document.setField("item_title","测试商品_cloud");
        document.setField("item_price",12300);
        solrServer.add(document);
        solrServer.commit();


    }


    @Test
    public void testQueryDocument() throws Exception{
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");

        solrServer.setDefaultCollection("collection2");

        SolrQuery query = new SolrQuery();
        query.setQuery("id");
        query.set("q","*:*");
        QueryResponse response = solrServer.query(query);

        SolrDocumentList docs = response.getResults();
        System.out.println("AllNum: "+ docs.getNumFound());
        for (SolrDocument doc : docs){
            System.out.println(doc.get("id"));
            System.out.println(doc.get("item_title"));
            System.out.println(doc.get("item_price"));
            System.out.println(doc.get("item_image"));
        }









*/


}
