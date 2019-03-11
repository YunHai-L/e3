package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGroupResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/10/24 21:34
 * @Description:
 */
public interface ItemService {

//    获取对应的规格描述
    E3Result getItemParam(Long itemId);

//    通过id获取
    TbItem getItemById(long itemId);
    TbItemDesc getItemDescById(long itemId);

//    起始页和结束页 获取对象
    EasyUIDataGroupResult getItemList(int page, int rows);

    /**
     * 向dao插入数据
     * @param item : 商品类型
     * @param desc : 商品的描述表
     * @return: 结果
     */
    E3Result addItem(TbItem item, String desc);


    /**
     * 删除指定商品
     * @param ids: 商品的id集合
     * @return : 结果
     */
    E3Result deleteItem(List<Long> ids);
}
