package cn.e3mall.controller;

import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: YunHai
 * @Date: 2018/11/28 14:17
 * @Description:
 */
@Controller
public class ItemDescController {
    @Autowired
    ItemDescService itemDescService;

    @RequestMapping("/rest/item/query/item/desc/{itemDescId}")
    @ResponseBody
    public TbItemDesc getItemDescById(@PathVariable Long itemDescId){
        return itemDescService.getItemDescById(itemDescId);
    }
}
