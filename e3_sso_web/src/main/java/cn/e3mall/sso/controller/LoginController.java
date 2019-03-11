package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: YunHai
 * @Date: 2018/12/26 02:52
 * @Description: 登录页面
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model){
//        若redirect有值, 登录成功后会跳转到对应的页面(重定向)
        model.addAttribute("redirect", redirect);
        return "login";
    }


    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(HttpServletRequest request, HttpServletResponse response, String username, String password){
        E3Result e3Result = loginService.userLogin(username, password);
//        如果成功获取到用户数据
        if (e3Result.getStatus() == 200){
            String token = (String) e3Result.getData();
//            将token置入cookie
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }

        return e3Result;
    }


}
