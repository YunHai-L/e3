package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * @Auther: YunHai
 * @Date: 2018/12/24 20:49
 * @Description:
 */
public interface RegisterService {
//    验证数据
    E3Result checkData(String param, int type);

//    注册
    E3Result register(TbUser user);

}
