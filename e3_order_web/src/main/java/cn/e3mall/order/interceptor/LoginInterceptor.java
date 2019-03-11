package cn.e3mall.order.interceptor;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.LoginService;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: YunHai
 * @Date: 2019/1/2 18:06
 * @Description: 登录验证
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CartService cartService;
    @Value("${SSO_URL}")
    private String SSO_URL;


    /**
     * 判断是否登录
     * 若未登录 redirect至登录页面 并提示返回当前页面
     * 若已登录 放行
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        从cookie获取token
        String token = CookieUtils.getCookieValue(request, "token");
        if (StringUtils.isBlank(token)){
//            未登录
            response.sendRedirect(SSO_URL + "/page/login?redirect="+ request.getRequestURL());
            return false;
        }
        E3Result e3Result= tokenService.getUserByToken(token);
        if (e3Result.getStatus() != 200){
//            登录过期
            response.sendRedirect(SSO_URL + "/page/login?redirect="+ request.getRequestURL());
            return false;
        }
//        获取user  并传入request
        TbUser user = (TbUser) e3Result.getData();
        request.setAttribute("user", user);

//        判断购物车是否有商品
        String jsonCartList = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isNotBlank(jsonCartList)){
//            如果不是空  cookie有购物车数据. 需要合并
            cartService.mergeCart(user.getId(),JsonUtils.jsonToList(jsonCartList, TbItem.class));
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
