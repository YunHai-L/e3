package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/11/12 01:05
 * @Description:
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long patentId);
}
