package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 * @Auther: YunHai
 * @Date: 2018/12/2 02:25
 * @Description:
 */
@Controller
public class ContentCatController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 节点展示
     * @param parentId
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId){
        return contentCategoryService.getContentCatList(parentId);
    }


    @RequestMapping(value = "/content/category/create")
    @ResponseBody
    public E3Result createContentCategory(Long parentId, String name){
        return contentCategoryService.addContentCategory(parentId,name);
    }


    /**
     * 删除指定节点 (判断父节点和子节点并对应修改删除)
     * @param id
     */
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public void deleteContentCategory(Long id){
        contentCategoryService.deleteContentCategory(id);
//        return "0";
    }

    /**
     * 改名
     * @param id
     * @param name
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public String updateContentCategory(Long id, String name){
        contentCategoryService.updateContentCategory(id,name);
        return "0";
    }





}
