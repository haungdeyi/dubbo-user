package com.huangdeyi.dubbo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectRedis {
    private static JedisPool jedisPool;

    static{
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(20);
        //
        jedisPoolConfig.setMaxIdle(10);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.43.66",6379);
    }
    public static Jedis getJedis() throws Exception {
        if(jedisPool != null) {
            return jedisPool.getResource();
        }
        else{
            throw new Exception("获取jedis失败");
        }
    }
}
