package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @Auther: YunHai
 * @Date: 2018/12/28 02:44
 * @Description: 通过token获取对应信息
 */
public interface TokenService {
    E3Result getUserByToken(String token);
}
