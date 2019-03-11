package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * @Auther: YunHai
 * @Date: 2019/1/2 21:26
 * @Description:
 */
public interface OrderService {

    E3Result createOrder(OrderInfo orderInfo);
}
