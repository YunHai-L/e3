package cn.e3mall.order.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2019/1/2 21:27
 * @Description: 订单处理service
 */
@Service
public class OrderServiceImpl implements OrderService {
//    三个数据库对应的mybatis
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private String ORDER_DETAIL_ID_GEN_KEY;

    /**
     * 生成订单 并写入数据库
     * @param orderInfo
     * @return
     */
    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
//        将订单, 订单商品, 订单地址 三表的数据 补全并写入数据库

//        补全订单属性------
//        生成order的订单号(用redis的incr生成)
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        orderInfo.setOrderId(orderId);
//        1.未付款, 2.已付款, 3.未发货, 4.已发货, 5.交易成功, 6.交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
//        插入订单表
        orderMapper.insert(orderInfo);

//        补全订单商品属性
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for(TbOrderItem orderItem : orderItems){
//            订单商品id
            orderItem.setId(jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString());
//            所属订单id
            orderItem.setOrderId(orderId);
//            向订单商品表插入数据
            orderItemMapper.insert(orderItem);
        }

//        补全订单地址信息
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
//        插入订单地址
        orderShippingMapper.insert(orderShipping);


        return E3Result.ok(orderId);
    }
}
