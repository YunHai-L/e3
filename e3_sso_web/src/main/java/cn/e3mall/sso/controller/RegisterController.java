package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: YunHai
 * @Date: 2018/12/24 17:34
 * @Description: 注册功能
 */
@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String showRegister(){ return "register";}


    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkData(@PathVariable String param, @PathVariable int type){
        return registerService.checkData(param, type);
    }

    @RequestMapping(value = "/user/register", method= RequestMethod.POST)
    @ResponseBody
    public E3Result register(TbUser user){
        return registerService.register(user);
    }


}
