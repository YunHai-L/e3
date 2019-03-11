package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: YunHai
 * @Date: 2018/12/28 02:54
 * @Description: 根据Token查询用户信息
 */
@Controller
public class TokenController {
    @Autowired
    private TokenService tokenService;

//    @RequestMapping(value = "/user/token/{token}", produces = "application/json;charset=utf-8")
    @RequestMapping(value = "/user/token/{token}"/*, produces = MediaType.APPLICATION_JSON_UTF8_VALUE*/)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback){
        E3Result result = tokenService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)){
//            jsonp请求, 将结果封装为js语句
//            return callback + "(" + JsonUtils.objectToJson(result) + ");";
//            创建对象 传入被封装的对象
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
//            设置方法名
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }

//        return JsonUtils.objectToJson(result);
        return result;
    }
}
