package me.silentdoer.redisrabbitmq;


import com.rabbitmq.client.ConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/11/18 9:03 PM
 */
public class Entrance {
    private static Lock poolLock = new ReentrantLock();
    private static Lock jedisLock = new ReentrantLock();
    public static Pool<Jedis> jedisPool;

    public static void main(String[] args) throws UnsupportedEncodingException {
        initJedisPool();
        Jedis jedis = jedisPool.getResource();
        jedis.set("中国".getBytes("utf8"), "我里试试试".getBytes("utf8"));
        byte[] content = jedis.get("中国".getBytes("utf8"));
        System.out.println(new String(content, "UTF8"));
        jedis.close();
    }

    public static Jedis getJedis(){
        Jedis jedis = null;
        // assert pool is initialized
        jedisLock.lock();  // 没有意义的加锁，人家内部已经做好了并发机制
        try{
            jedis = jedisPool.getResource();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            jedisLock.unlock();
        }
        return jedis;
    }

    // 不要用此方法，因为防止jedis并不是由pool创建，如果说忘记了close，那么也没关系因为它就变成了一个idle，过一定时间也一样被回收
    public static void reuseJedis(final Jedis jedis){
        // assert pool is initialized
        //jedisPool.returnResource();  // 已过期，正确的回收方式是jedis.close()
    }

    public static void initJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(24);  // 最大空闲数（这里应该还有个设置多久后认定是空闲的）
        config.setMaxTotal(48);  // 最大连接数
        config.setMaxWaitMillis(3000);  // 当连接不够时等待的时长
        config.setTestOnBorrow(false);
        if(jedisPool == null) {
            poolLock.lock();  // 这一个其实也没有什么意义，只需将初始化的工作放到那种只执行一次的地方即可，如listener/init之类的
            try {
                jedisPool = new JedisPool(config, "localhost", 6379, 10000, null);  // 最后一个为auth，没有即为null
            } catch (Exception ex) {
                ex.printStackTrace();
            }finally {
                poolLock.unlock();
            }
        }
    }
}
