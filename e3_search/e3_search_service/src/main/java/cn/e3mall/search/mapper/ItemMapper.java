package cn.e3mall.search.mapper;

import cn.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/12 17:38
 * @Description:
 */
public interface ItemMapper {
    List<SearchItem> getItemList();

    SearchItem getItemById(long itenId);


}
