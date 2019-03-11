package cn.e3mall.portal.controller;

import cn.e3mall.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Auther: YunHai
 * @Date: 2018/11/30 15:15
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @Value("${CONTENT_TURN_ID}")
    private int CONTENT_TURN_ID;

    @RequestMapping("/index")
    public String showIndex(Model model){
//        查询内容列表(轮播图广告))

        model.addAttribute("ad1List", contentService.getContentListByCid(CONTENT_TURN_ID));
        return "index";
    }
}
