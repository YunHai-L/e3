package cn.e3mall.redis;

import cn.e3mall.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Auther: YunHai
 * @Date: 2018/12/9 13:49
 * @Description:
 */
public class JedisClientTest {

    @Test
    public void testJedisClient() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
//        jedisClient.set("test","456");
        System.out.println(jedisClient.get("test12334"));
    }
}
