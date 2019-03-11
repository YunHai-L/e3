package cn.e3mall.content.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/4 13:37
 * @Description:
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public E3Result addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);

//        缓存同步  删除对应的数据
        jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());

        return E3Result.ok();
    }


    /**
     * 根据id查询对应的content集合
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentListByCid(long cid) {
//        条件设置
        try {
            String json = jedisClient.hget(CONTENT_LIST,cid+"");
            if(StringUtils.isNotBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return  list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(cid);
//        执行查询
        List<TbContent> tbContentList = contentMapper.selectByExample(example);
        try{
            jedisClient.hset(CONTENT_LIST, cid+"", JsonUtils.objectToJson(tbContentList));
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbContentList;
    }
}
