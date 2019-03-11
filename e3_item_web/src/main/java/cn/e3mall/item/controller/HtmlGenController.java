package cn.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: YunHai
 * @Date: 2018/12/21 16:45
 * @Description:
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genhtml() throws Exception{
        Configuration configuration = freeMarkerConfigurer.getConfiguration();

        Writer writer = new FileWriter(new File("D:\\ideaWork\\e3\\e3_item_web\\src\\main\\webapp\\WEB-INF\\jsp\\hello.html"));
        Template template = configuration.getTemplate("hello.ftl");
        Map data = new HashMap<>();
        data.put("hello", 123654);
        template.process(data, writer);
        writer.close();

        return "OK";
    }
}
