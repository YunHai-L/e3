package cn.e3mall.item.listener;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: YunHai
 * @Date: 2018/12/21 19:50
 * @Description:
 */
public class HtmlGenListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            Long itemId = new Long(textMessage.getText());

//            等待事务先提交
            Thread.sleep(1000);

//        创建模板
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");

//        通过id获取商品对象和商品描述对象
            Item item = new Item(itemService.getItemById(itemId));
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);

//        通过对象生成静态页面
            Writer out = new FileWriter(new File(HTML_GEN_PATH + itemId + ".html"));
            Map data = new HashMap();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            template.process(data, out);

            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
