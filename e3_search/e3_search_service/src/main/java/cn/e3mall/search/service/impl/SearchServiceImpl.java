package cn.e3mall.search.service.impl;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: YunHai
 * @Date: 2018/12/14 13:40
 * @Description:
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) throws Exception {
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
        if(page <= 0) page = 1;
//        起始页
        query.setStart((page - 1) * rows);
        query.setRows(rows);
//        默认域
        query.set("df","item_title");
//        高亮 title 红色
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color : red\">");
        query.setHighlightSimplePost("</em>");

//        获取分页  设置总页数
        SearchResult result = searchDao.search(query);
        long recordCount = result.getRecordCount();
        int totalPage = (int) (recordCount / rows);
        if (recordCount % rows != 0) totalPage ++;

        result.setTotalCount(totalPage);


        return result;
    }
}
