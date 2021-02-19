package org.webmaple.worker.config;

import redis.clients.jedis.JedisPool;

/**
 * @author lyifee
 * 使用worker构建爬虫时需要实现此接口
 * 提供爬虫需要使用的jedispool
 * on 2021/2/19
 */
public interface JedisSPI {
    JedisPool getJedisPool();
}
