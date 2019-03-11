package cn.e3mall.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGroupResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.mapper.TbItemParamMapper;
import cn.e3mall.pojo.*;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @Auther: YunHai
 * @Date: 2018/10/24 21:37
 * @Description:商品管理service
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamMapper tbItemParamMapper;
//    生产者
    @Autowired
    private JmsTemplate jmsTemplate;
//    发送一对多
    @Resource
    private Destination topicDestination;

//    redis缓存
    @Autowired
    private JedisClient jedisClient;
//    缓存key的前缀
    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
//    key的过期时间
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;

    @Override
    public E3Result getItemParam(Long itemId) {
//        条件
        TbItemParamExample itemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = itemParamExample.createCriteria();
//        获取指定的商品  并获取对应的cid  将cid赋值
        criteria.andItemCatIdEqualTo(getItemById(itemId).getCid());

        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(itemParamExample);

        if (list.size() != 0){
            return E3Result.ok(list.get(0));
        }
        return E3Result.build(404,"没有找到该规格");
    }

    @Override
    public TbItem getItemById(long itemId) {
//        return tbItemMapper.selectByPrimaryKey(itemId);
//        条件查询
        try{
            String itemJson = jedisClient.get(ITEM_CACHE_EXPIRE + ":" + itemId + ":BASE");
//            bug: 若正好查询后 时间到了清空  会导致这里出错
            if(StringUtils.isNotBlank(itemJson)){
                jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
                return JsonUtils.jsonToPojo(itemJson, TbItem.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);



        if (tbItems != null && tbItems.size() != 0){
            try{
//                将item放入缓存 并设置失效时间
                jedisClient.set(REDIS_ITEM_PRE + ":"+itemId + ":BASE",JsonUtils.objectToJson(tbItems.get(0)));
                jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
            }catch(Exception e){
                e.printStackTrace();
            }
            return tbItems.get(0);
        }

        return null;
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        try{
            String itemDescJson = jedisClient.get(ITEM_CACHE_EXPIRE + ":" + itemId + ":DESC");
//            bug: 若正好查询后 时间到了清空  会导致这里出错
            if(StringUtils.isNotBlank(itemDescJson)){
                jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
                return JsonUtils.jsonToPojo(itemDescJson, TbItemDesc.class);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        try{
//                将item放入缓存 并设置失效时间
            jedisClient.set(REDIS_ITEM_PRE + ":"+itemId + ":DESC",JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
        }catch(Exception e){
            e.printStackTrace();
        }

        return tbItemDesc;
    }

    @Override
    public EasyUIDataGroupResult getItemList(int page, int rows) {
//        设置分页
        PageHelper.startPage(page,rows);
//        执行查询
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
//        取结果
        EasyUIDataGroupResult result = new EasyUIDataGroupResult();
        result.setTotal(new PageInfo<>(list).getTotal());
        result.setRows(list);
        return result;
    }



    @Override
//    向表中添加商品
    public E3Result addItem(TbItem item, String desc) {
//        设置id  设置商品状态  (补全属性)
//        通过工具类获取id
        final long itemId = IDUtils.genItemId();
        item.setId(itemId);
//        1:正常  2:下架  3:删除
        item.setStatus((byte)1);

        item.setCreated(new Date());
        item.setUpdated(new Date());
//        插入
        tbItemMapper.insert(item);

//        同时  也要插入对应的商品描述表
        TbItemDesc itemDesc = new TbItemDesc();
//        id, 内容, 时间
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
//        插入
        tbItemDescMapper.insert(itemDesc);

        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(itemId + "");

            }
        });


        return E3Result.ok();
    }




    @Override
    /**
     * 删除指定的商品
     */
    public E3Result deleteItem(List<Long> ids) {
//        商品表删除  创建条件
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria itemExampleCriteria = tbItemExample.createCriteria();
//        in(id1, id2)
        itemExampleCriteria.andIdIn(ids);

        tbItemMapper.deleteByExample(tbItemExample);

//        商品描述表删除  创建条件
        TbItemDescExample itemDescExample = new TbItemDescExample();
        TbItemDescExample.Criteria itemDescCriteria = itemDescExample.createCriteria();
        itemDescCriteria.andItemIdIn(ids);

        tbItemDescMapper.deleteByExample(itemDescExample);
        return E3Result.ok();
    }
}
