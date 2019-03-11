package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/29 19:57
 * @Description: 购物车Controller
 */
@Controller
public class CartController {
    @Autowired
    private ItemService itemService;
    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;
    @Autowired
    private CartService cartService;

    /**
     * 添加购物车商品
     * @param request
     * @param response
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(HttpServletRequest request, HttpServletResponse response, @PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num){
//        判断用户是否登录
//        如果有登录 执行这里
//        否则 执行下面
        TbUser user = (TbUser) request.getAttribute("user");
        if(user != null){
//            如果有用户 调用service的方法添加
            cartService.addCart(user.getId(), itemId, num);
            return "cartSuccess";
        }


//        获取购物车里的列表
        List<TbItem> cartList = getCartListFromCookie(request);
//        判断里面是否有itemId
        boolean flag = false;
        for (TbItem item : cartList){
//        如果有 + num
            if (item.getId() == itemId.longValue()){
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
//        如果没有 则添加
        if (!flag){
            TbItem tbItem = itemService.getItemById(itemId);
//            设置图片
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) tbItem.setImage(image.split(",")[0]);
//            设置数量
            tbItem.setNum(num);
//            添加
            cartList.add(tbItem);
        }

//            写入
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
//        添加成功页面
        return "cartSuccess";
    }


//    跳转到购物车页面
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response){
//        获取cookie购物车商品列表
        List<TbItem> cartList = getCartListFromCookie(request);

//        判断是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null){
//            登录状态
//            将cookie和redis的值合并
            cartService.mergeCart(user.getId(), cartList);
//            删除cookie中的cart
            CookieUtils.deleteCookie(request, response, "cart");
//            获取购物车列表
            cartList = cartService.getCartList(user.getId());
//            返回

        }


//        将商品列表置入request
        request.setAttribute("cartList", cartList);

//        购物车页面
        return "cart";

    }


    /**
     * 更新购物车的商品数量
     * @param itemId: 商品id
     * @param num: 更新后的商品数量
     * @param request
     * @param response
     * @return: E3Result
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable int num, HttpServletRequest request, HttpServletResponse response){
//        如果user存在 执行redis操作
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null){
            return cartService.updateCartNum(user.getId(), itemId, num);
        }

        List<TbItem> cartList = getCartListFromCookie(request);

        for (TbItem tbItem : cartList){
            if (tbItem.getId().longValue() == itemId){
                tbItem.setNum(num);
                break;
            }
        }
//        写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);

        return E3Result.ok();
    }


    /**
     * 删除购物车中指定的商品
     *
     * @param itemId:  商品id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
//        如果user存在 执行redis操作
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null){
            cartService.deleteCartNum(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }

//        获取
        List<TbItem> cartList = getCartListFromCookie(request);
//        删除
        for (TbItem tbItem : cartList) {
            if (tbItem.getId().longValue() == itemId) {
                cartList.remove(tbItem);
                break;
            }
        }
//        写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);

        return "redirect:/cart/cart.html";
    }




    public List<TbItem> getCartListFromCookie(HttpServletRequest request){
//        获取购物车信息
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isBlank(cart)) return new ArrayList<>();
//        返回
        return JsonUtils.jsonToList(cart, TbItem.class);
    }

}
