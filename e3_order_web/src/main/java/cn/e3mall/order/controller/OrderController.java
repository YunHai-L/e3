package cn.e3mall.order.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2019/1/2 11:24
 * @Description: 订单服务
 */
@Controller
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    /**
     * 跳转到订单结算页面
     * @return
     */
    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request){
//        获取用户信息
        TbUser user = (TbUser) request.getAttribute("user");
//        调用cart服务 获取用户的购物车信息
        List<TbItem> cartList = cartService.getCartList(user.getId());
//        返回
        request.setAttribute("cartList", cartList);
        return "order-cart";
    }


    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request){
//        获取用户信息 并设置订单对应的信息
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setBuyerNick(user.getUsername());
        orderInfo.setUserId(user.getId());

        E3Result e3Result = orderService.createOrder(orderInfo);

        if (e3Result.getStatus() == 200){
            cartService.deleteCartItem(user.getId());
        }
        request.setAttribute("orderId",e3Result.getData() );
        request.setAttribute("payment",orderInfo.getPayment() );

        return "success";
    }


}
