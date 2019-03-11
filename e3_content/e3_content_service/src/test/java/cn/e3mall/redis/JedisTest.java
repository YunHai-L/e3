package cn.e3mall.redis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: YunHai
 * @Date: 2018/12/8 21:02
 * @Description:
 */
public class JedisTest {
    @Test
    public void testJedisCluster()throws Exception{
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.129",7001));
        nodes.add(new HostAndPort("192.168.25.129",7002));
        nodes.add(new HostAndPort("192.168.25.129",7003));
        nodes.add(new HostAndPort("192.168.25.129",7004));
        nodes.add(new HostAndPort("192.168.25.129",7005));
        nodes.add(new HostAndPort("192.168.25.129",7006));
        JedisCluster cluster = new JedisCluster(nodes);
//        cluster.set("testCluster","123");
        System.out.println(cluster.get("testCluster"));
        cluster.close();
    }
}
