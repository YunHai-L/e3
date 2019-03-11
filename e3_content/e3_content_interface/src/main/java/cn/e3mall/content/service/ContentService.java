package cn.e3mall.content.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/4 13:35
 * @Description:
 */
public interface ContentService {
//    向数据库插入数据
    E3Result addContent(TbContent content);

//    通过cid获取对应的content
    List<TbContent> getContentListByCid(long cid);
}
