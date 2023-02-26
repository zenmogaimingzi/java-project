package com.example.demo.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author gaosh
 */
public class RedisUtils {
    private static final String REDIS_HOST = "127.0.0.1";
    private static final int REDIS_PORT = 6379;
    private static final int MAX_TOTAL = 100;
    private static final int MAX_IDLE = 10;
    private static final int MIN_IDLE = 1;
    private static final int MAX_WAIT_MILLIS = 1000;
    private static final int TIMEOUT = 10000;
    private static final boolean TEST_ON_BORROW = true;
    private static final boolean TEST_ON_RETURN = true;
    
    private static final JedisPool jedisPool = createJedisPool();

    private static JedisPool createJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_TOTAL);
        config.setMaxIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        config.setMaxWaitMillis(MAX_WAIT_MILLIS);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setTestOnReturn(TEST_ON_RETURN);
        return new JedisPool(config, REDIS_HOST, REDIS_PORT, TIMEOUT);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }



}
