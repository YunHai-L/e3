package cn.e3mall.service.impl;

import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/11/28 14:19
 * @Description:
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private TbItemDescMapper itemDescMapper;
//    通过id获取对象
    @Override
    public TbItemDesc getItemDescById(Long itemDescId) {
//        条件
        TbItemDescExample itemDescExample = new TbItemDescExample();
        TbItemDescExample.Criteria criteria = itemDescExample.createCriteria();
        criteria.andItemIdEqualTo(itemDescId);
//        查询
        List<TbItemDesc> list = itemDescMapper.selectByExample(itemDescExample);
//        判断
        if(list.size() != 0)
            return list.get(0);
        return null;
    }
}
