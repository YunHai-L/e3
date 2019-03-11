package cn.e3mall.order.pojo;

import cn.e3mall.pojo.TbOrder;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2019/1/2 21:07
 * @Description:
 */
public class OrderInfo extends TbOrder{
    private List<TbOrderItem> orderItems;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    private TbOrderShipping orderShipping;
}
