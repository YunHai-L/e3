package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @Auther: YunHai
 * @Date: 2018/12/26 02:54
 * @Description:
 */
public interface LoginService {

//    登录
    E3Result userLogin(String username, String password);

}
