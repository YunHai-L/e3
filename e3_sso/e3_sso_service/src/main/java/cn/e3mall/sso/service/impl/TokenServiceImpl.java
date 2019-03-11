package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: YunHai
 * @Date: 2018/12/28 02:45
 * @Description:
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JedisClient jedisClient;
//    redis过期时间
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

//        通过token获取对应信息
    @Override
    public E3Result getUserByToken(String token) {
//        从redis获取token对应的值
        String tokenValue = jedisClient.get("SESSION:" + token);
        if (StringUtils.isBlank(tokenValue)){
            return E3Result.build(201, "用户登录过期");
        }

        TbUser tbUser = JsonUtils.jsonToPojo(tokenValue, TbUser.class);
//        如果有获取到 重置过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);

        return E3Result.ok(tbUser);
    }
}
