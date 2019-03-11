package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * @Auther: YunHai
 * @Date: 2018/12/14 13:33
 * @Description:
 */
public interface SearchService {

    SearchResult search(String keyword, int page, int rows) throws Exception;
}
