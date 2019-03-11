package cn.e3mall.sso.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/24 20:58
 * @Description:
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private TbUserMapper tbUserMapper;

//    验证数据库重复
    @Override
    public E3Result checkData(String param, int type) {
        TbUserExample example = new TbUserExample();

        switch (type){
//            1用户名 2手机 3邮箱
            case 1: example.createCriteria().andUsernameEqualTo(param);
                break;
            case 2: example.createCriteria().andPhoneEqualTo(param);
                break;
            case 3: example.createCriteria().andEmailEqualTo(param);
                break;
            default:
                return E3Result.build(400, "RegisterService: type Exception: 不存在的类型");
        }
//        查询
        List<TbUser> list = tbUserMapper.selectByExample(example);

//        如果重复  返回false
        if (list != null && list.size() != 0) return E3Result.ok(false);
        return E3Result.ok(true);
    }

//    注册
    @Override
    public E3Result register(TbUser user) {
//        判断值是否合法
//        1:空值
        if ("".equals(user.getUsername()) || "".equals(user.getPassword()) || "".equals(user.getPhone())){
            return E3Result.build(400, "RegisterService:  value = null");
        }

//        2:重复
        if (!(boolean)checkData(user.getUsername(), 1).getData()){
            return E3Result.build(400, "RegisterService: 用户名已被占用");
        }else if (!(boolean)checkData(user.getPhone(), 2).getData()){
            return E3Result.build(400, "RegisterService: 手机号已被占用");
        }
//        补全信息 createDate Updated
        user.setCreated(new Date());
        user.setUpdated(new Date());
//        密码加密 md5
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);

        tbUserMapper.insert(user);
            return E3Result.ok();

    }



}
