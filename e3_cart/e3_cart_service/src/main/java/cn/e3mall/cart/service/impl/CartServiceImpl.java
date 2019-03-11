package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2019/1/1 00:33
 * @Description: 购物车处理
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    /**
     * add item to cart
     * @param userId: 用户的id
     * @param itemId: 商品的id
     * @param num: 商品数量
     * @return
     */
    @Override
    public E3Result addCart(long userId, long itemId, int num) {
//        insert cart to redis
//        type is hash, key : userId, field : itemId, value : itemJson
//        判断商品是否存在
        if(jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "")){
//        若存在 + num
            String item = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            TbItem tbItem = JsonUtils.jsonToPojo(item, TbItem.class);
            tbItem.setNum(tbItem.getNum() + num);
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));

            return E3Result.ok();
        }

//        若不存在 ----------------------
//        查询商品
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
//        设置num
        tbItem.setNum(num);
//        获取图片
        String image = tbItem.getImage();
        if (StringUtils.isNotBlank(image)) {
            tbItem.setImage(image.split(",")[0]);
        }

//        添加至购物车
        jedisClient.hset(REDIS_CART_PRE +":" +userId, itemId +"", JsonUtils.objectToJson(tbItem));
//        返回ok


        return E3Result.ok();
    }


    /**
     * 合并cookie和redis购物车
     * @param userId: 用户id
     * @param itemList: cookie商品列表
     * @return
     */
    @Override
    public E3Result mergeCart(long userId, List<TbItem> itemList) {
//        循环调用add方法即可
        for (TbItem item : itemList) {
            addCart(userId, item.getId(), item.getNum());
        }
        return E3Result.ok();
    }

    /**
     * 获取用户的购物车列表
     * @param userId: 用户id
     * @return
     */
    @Override
    public List<TbItem> getCartList(long userId) {
//        获取hash
        List<String> hvals = jedisClient.hvals(REDIS_CART_PRE +":" +userId);
//        创建一个列表 用于返回
        List<TbItem> itemList = new ArrayList<>(hvals.size());
//        遍历转换
        for (String json : hvals) itemList.add(JsonUtils.jsonToPojo(json, TbItem.class));

        return itemList;
    }

    /**
     * 更新购物车商品的数量
     * @param userId: 用户id
     * @param itemId: 商品id
     * @param num: 更新后的数量
     * @return
     */
    @Override
    public E3Result updateCartNum(long userId, long itemId, int num) {
//        获取json
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
//        更新
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        item.setNum(num);
//        写入
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        return E3Result.ok();
    }

    /**
     * 删除指定的商品 from cart
     * @param userId: 用户id
     * @param itemId: 商品id
     * @return
     */
    @Override
    public E3Result deleteCartNum(long userId, long itemId) {
        jedisClient.hdel(REDIS_CART_PRE +":" +userId, itemId +"");
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(long userId) {
        jedisClient.del(REDIS_CART_PRE +":" +userId);
        return E3Result.ok();
    }
}
