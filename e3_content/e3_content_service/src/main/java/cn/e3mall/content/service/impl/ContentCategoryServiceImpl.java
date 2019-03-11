package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理
 * @Auther: YunHai
 * @Date: 2018/12/2 02:11
 * @Description:
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> easyUiTreeNodeList = new ArrayList<>(list.size());
        for(TbContentCategory category : list){
            EasyUITreeNode easyTemp = new EasyUITreeNode();
            easyTemp.setId(category.getId());
            easyTemp.setText(category.getName());
            easyTemp.setState(category.getIsParent() ? "closed" : "open");
            easyUiTreeNodeList.add(easyTemp);
        }
        return easyUiTreeNodeList;
    }

    @Override
    public E3Result addContentCategory(long parentId, String name) {
        TbContentCategory category = new TbContentCategory();

//        赋值
        category.setName(name);
        category.setParentId(parentId);
        category.setUpdated(new Date());
        category.setCreated(new Date());
        category.setIsParent(false);
//        1正常  2删除
        category.setStatus(1);
//        默认排序 1
        category.setSortOrder(1);
//        插入数据库
        contentCategoryMapper.insertSelective(category);

//        获取父类 并判断
        TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
//        如果不是父类  则改为父类
        if(!parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }

        return E3Result.ok(category);
    }

    /**
     * 删除指定节点
     * 注: 若是父节点  递归删除
     *  若该节点的父节点 没有其他节点  改isparent为false
     * @param id
     */
    @Override
    public void deleteContentCategory(long id) {
        TbContentCategory source = contentCategoryMapper.selectByPrimaryKey(id);
        if(source.getIsParent()){
//            如果是父节点 调用递归方法将其删除
            deleteSonContentCategory(id);
        }
//        删除指定节点
        contentCategoryMapper.deleteByPrimaryKey(id);

//        通过父id  获取所有父节点相同的兄弟节点
        TbContentCategoryExample bortherCategory = new TbContentCategoryExample();
        bortherCategory.createCriteria().andParentIdEqualTo(source.getParentId());
        List<TbContentCategory> bortherList = contentCategoryMapper.selectByExample(bortherCategory);

//        如果没有兄弟节点 将父节点改为非父节点
        if(bortherList.size() == 0){
//            获取
            TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(source.getParentId());
            parentContentCategory.setIsParent(false);
//            更新
            contentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }





    }


    /**
     * 指定的节点更名
     * @param id
     * @param name
     */
    @Override
    public void updateContentCategory(long id, String name) {
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        category.setName(name);
        contentCategoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 传入父节点的id  删除所有非父的子节点  并循环调用自己 传入是父节点的子节点
     * @param id
     */
    public void deleteSonContentCategory(long id){
        TbContentCategoryExample deleteExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria deleteCriteria = deleteExample.createCriteria();
//        将所有非父的子节点删除
        deleteCriteria.andParentIdEqualTo(id);
        deleteCriteria.andIsParentEqualTo(false);
        contentCategoryMapper.deleteByExample(deleteExample);

//        余下的都是为父节点的子节点
        TbContentCategoryExample sonExample = new TbContentCategoryExample();
        sonExample.createCriteria().andParentIdEqualTo(id);
//                获取所有该节点的子节点
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(sonExample);

//        如果size不为0  将这些子节点的id传入 循环调用
        if(list.size() != 0)
            for (TbContentCategory sonCategory : list) {
                deleteSonContentCategory(sonCategory.getId());
                contentCategoryMapper.deleteByPrimaryKey(sonCategory.getId());
            }
    }
}
