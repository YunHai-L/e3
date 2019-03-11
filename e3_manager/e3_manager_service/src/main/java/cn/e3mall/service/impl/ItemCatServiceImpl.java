package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/11/12 01:07
 * @Description:
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long patentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(patentId);

        List<TbItemCat> catList = tbItemCatMapper.selectByExample(example);
        List<EasyUITreeNode> results = new ArrayList<>(catList.size());

        for(TbItemCat cat : catList){
            EasyUITreeNode tempeutn = new EasyUITreeNode();
            tempeutn.setId(cat.getId());
            tempeutn.setText(cat.getName());
            tempeutn.setState(cat.getIsParent() ? "closed" : "open");
            results.add(tempeutn);
        }


        return results;
    }
}
