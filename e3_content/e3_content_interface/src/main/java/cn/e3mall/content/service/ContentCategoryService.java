package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/2 02:05
 * @Description:
 */
public interface ContentCategoryService {

//    获取节点
    List<EasyUITreeNode> getContentCatList(long parentId);
//    添加节点
    E3Result addContentCategory(long parentId, String name);
//    删除节点
    void deleteContentCategory(long id);
//    修改节点名
    void updateContentCategory(long id, String name);
}
