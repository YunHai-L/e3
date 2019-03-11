package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGroupResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/10/25 12:07
 * @Description: 商品管理Controller
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping({"/item/{itemId}","/rest/page/item-edit"})
    @ResponseBody
    public TbItem getItemById(@PathVariable @RequestParam("_") Long itemId){

        return itemService.getItemById(itemId);
    }







    @RequestMapping("/item/list")
    @ResponseBody
    /**
     * 获取指定商品集合
     */
    public EasyUIDataGroupResult getItemList(Integer page, Integer rows){
        return itemService.getItemList(page,rows);
    }



    @RequestMapping(value="/item/save", method = RequestMethod.POST)
    @ResponseBody
    /**
     * 添加商品功能
     */
    public E3Result addItem(TbItem item, String desc){
        return itemService.addItem(item, desc);
    }



    @RequestMapping(value = "/rest/item/delete", method = RequestMethod.POST)
    @ResponseBody
    /**
     * 删除商品功能
     */
    public E3Result deleteItem(String ids){
//        将传过来的字符串转为List<Long>
        String[] idsTemp = ids.split(",");
        List<Long> idsList = new ArrayList<>(idsTemp.length);
        for(String str : idsTemp){
            idsList.add(Long.valueOf(str));
        }
        return itemService.deleteItem(idsList);
    }


    @RequestMapping("/rest/item/param/item/query/{itemId}")
    @ResponseBody
    public E3Result getItemParam(@PathVariable Long itemId){
        E3Result e3 =itemService.getItemParam(itemId);

        return e3;
    }





}
