package com.huangdeyi.dubbo.redis;

import redis.clients.jedis.Jedis;

public class RedisDemo {
    public static void main(String[] args) throws Exception {
        Jedis jedis = ConnectRedis.getJedis();
        //jedis.set("name","二哈");
        String name = jedis.get("name");
        System.out.println(name);
    }
}
