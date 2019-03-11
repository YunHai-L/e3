package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: YunHai
 * @Date: 2018/12/14 10:35
 * @Description:
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws Exception{
        QueryResponse queryResponse = solrServer.query(query);
//        查询结果
        SolrDocumentList documentList = queryResponse.getResults();
//        查询总记录数
        long numFound = documentList.getNumFound();

//        设置总数
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);

//        创建List对象
        List<SearchItem> itemList = new ArrayList<>(documentList.size());
//        获取高亮
        Map<String, Map<String, List<String>>> highMap = queryResponse.getHighlighting();

        for (SolrDocument doc : documentList){
//            设置值
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) doc.get("id"));
            searchItem.setPrice((long)doc.get("item_price"));
            searchItem.setCategory_name((String)doc.get("item_category_name"));
            searchItem.setImage((String)doc.get("item_image"));
            searchItem.setSell_point((String)doc.get("item_sell_point"));

//            获取高亮部分
            List<String> titleHigh = highMap.get(doc.get("id")).get("item_title");
            String title;
            if (titleHigh != null && titleHigh.size() > 0){
                title = titleHigh.get(0);
            }else {
                title = (String) doc.get("item_title");
            }
            searchItem.setTitle(title);

//            添加进入list集合
            itemList.add(searchItem);
        }
//        add result
        result.setItenList(itemList);




        return result;
    }


}
