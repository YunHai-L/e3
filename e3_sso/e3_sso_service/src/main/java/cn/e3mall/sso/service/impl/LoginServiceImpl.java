package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.LoginService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Auther: YunHai
 * @Date: 2018/12/26 02:55
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {
//        1. 判断用户名密码正确 如果正确 则获取
        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(userExample);
        if (list == null || list.size() == 0)
            return E3Result.build(400, "账号或密码错误");

        TbUser user = list.get(0);
        if ( !user.getPassword().equals(DigestUtils.md5Hex(password.getBytes())))
            return E3Result.build(400, "账号或密码错误");

//        2. 如果正确 生成token
        String token = UUID.randomUUID().toString();
//        3. 信息写入redis
        user.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
//        4. 设置session的过期时间
        jedisClient.expire("SESSION:" + token,SESSION_EXPIRE );
//        返回token

            return E3Result.ok(token);
    }
}
