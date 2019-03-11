package cn.e3mall.cart.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/31 13:38
 * @Description:
 */
public interface CartService {
    /**
     * 添加购物车商品
     * @param userId: 用户的id
     * @param itemId: 商品的id
     * @param num: 商品数量
     * @return
     */
    E3Result addCart(long userId, long itemId, int num);

    /**
     * 合并cookie的商品和redis的商品 置入redis
     * @param userId: 用户id
     * @param itemList: cookie商品列表
     * @return
     */
    E3Result mergeCart(long userId, List<TbItem> itemList);

    /**
     * 获取用户的购物车列表
     * @param userId: 用户id
     * @return
     */
    List<TbItem> getCartList(long userId);

    /**
     * 更新商品数量
     * @param userId: 用户id
     * @param itemId: 商品id
     * @param num: 更新后的数量
     * @return
     */
    E3Result updateCartNum(long userId, long itemId, int num);

    /**
     * 删除指定商品
     * @param userId: 用户id
     * @param itemId: 商品id
     * @return
     */
    E3Result deleteCartNum(long userId, long itemId);


    E3Result deleteCartItem(long userId);

}
