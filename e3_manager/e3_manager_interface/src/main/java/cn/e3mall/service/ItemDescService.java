package cn.e3mall.service;

import cn.e3mall.pojo.TbItemDesc;

/**
 * @Auther: YunHai
 * @Date: 2018/11/28 14:17
 * @Description:
 */
public interface ItemDescService {
    /**
     * 通过id获取对象
     * @param itemDescId
     * @return: 商品描述对象
     */
    TbItemDesc getItemDescById(Long itemDescId);
}
