package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Auther: YunHai
 * @Date: 2018/12/17 23:56
 * @Description:
 */
public class MessageConsumer {

    @Test
    public void getMessage() throws Exception{
        new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        Thread.sleep(1000);

    }

}
