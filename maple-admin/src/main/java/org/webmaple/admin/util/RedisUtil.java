package org.webmaple.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;

import java.util.List;
import java.util.Set;

/**
 * @author lyifee
 * on 2021/1/20
 */
@Component
public class RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private JedisPool jedisPool;

    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public String get(String key,int indexdb) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            value = jedis.get(key);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return value;
    }

    public byte[] get(byte[] key,int indexdb) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            value = jedis.get(key);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return value;
    }

    public String set(String key, String value,int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.set(key, value);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "0";
        } finally {
            close(jedis);
        }
    }

    public String set(String key, String value, long expireTime, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.set(key, value, "NX", "EX", expireTime);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "0";
        } finally {
            close(jedis);
        }
    }

    public String set(byte[] key, byte[] value,int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.set(key, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
            return "0";
        } finally {
            close(jedis);
        }
    }

    public Long del(String key, int indexdb) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.del(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * <p>
     * 通过key向list头部添加字符串
     * </p>
     *
     * @param key
     * @param strs
     *            可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long lpush(int indexdb, String key, String... strs) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            res = jedis.lpush(key, strs);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key向list尾部添加字符串
     * </p>
     *
     * @param key
     * @param strs
     *            可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long rpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.rpush(key, strs);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key在list指定的位置之前或者之后 添加字符串元素
     * </p>
     *
     * @param key
     * @param where
     *            LIST_POSITION枚举类型
     * @param pivot
     *            list里面的value
     * @param value
     *            添加的value
     * @return
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot,
                        String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.linsert(key, where, pivot, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key设置list指定下标位置的value
     * </p>
     * <p>
     * 如果下标超过list里面value的个数则报错
     * </p>
     *
     * @param key
     * @param index
     *            从0开始
     * @param value
     * @return 成功返回OK
     */
    public String lset(String key, Long index, String value) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lset(key, index, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key从对应的list中删除指定的count个 和 value相同的元素
     * </p>
     *
     * @param key
     * @param count
     *            当count为0时删除全部
     * @param value
     * @return 返回被删除的个数
     */
    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lrem(key, count, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key保留list中从strat下标开始到end下标结束的value值
     * </p>
     *
     * @param key
     * @param start
     * @param end
     * @return 成功返回OK
     */
    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.ltrim(key, start, end);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key从list的头部删除一个value,并返回该value
     * </p>
     *
     * @param key
     * @return
     */
    synchronized public String lpop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lpop(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key从list尾部删除一个value,并返回该元素
     * </p>
     *
     * @param key
     * @return
     */
    synchronized public String rpop(String key, int indexdb) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            res = jedis.rpop(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key从一个list的尾部删除一个value并添加到另一个list的头部,并返回该value
     * </p>
     * <p>
     * 如果第一个list为空或者不存在则返回null
     * </p>
     *
     * @param srckey
     * @param dstkey
     * @return
     */
    public String rpoplpush(String srckey, String dstkey, int indexdb) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            res = jedis.rpoplpush(srckey, dstkey);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取list中指定下标位置的value
     * </p>
     *
     * @param key
     * @param index
     * @return 如果没有返回null
     */
    public String lindex(String key, long index) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.lindex(key, index);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key返回list的长度
     * </p>
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.llen(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取list指定下标位置的value
     * </p>
     * <p>
     * 如果start 为 0 end 为 -1 则返回全部的list中的value
     * </p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end, int indexdb) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            res = jedis.lrange(key, start, end);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 将列表 key 下标为 index 的元素的值设置为 value
     * </p>
     *
     * @param key
     * @param index
     * @param value
     * @return 操作成功返回 ok ，否则返回错误信息
     */
    public String lset(String key, long index, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lset(key, index, value);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * <p>
     * 返回给定排序后的结果
     * </p>
     *
     * @param key
     * @param sortingParameters
     * @return 返回列表形式的排序结果
     */
    public List<String> sort(String key, SortingParams sortingParameters) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sort(key, sortingParameters);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * <p>
     * 返回排序后的结果，排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较。
     * </p>
     *
     * @param key
     * @return 返回列表形式的排序结果
     */
    public List<String> sort(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sort(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * <p>
     * 通过key向指定的set中添加value
     * </p>
     *
     * @param key
     * @param members
     *            可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sadd(key, members);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key删除set中对应的value值
     * </p>
     *
     * @param key
     * @param members
     *            可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    public Long srem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.srem(key, members);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key随机删除一个set中的value并返回该值
     * </p>
     *
     * @param key
     * @return
     */
    public String spop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.spop(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取set中的差集
     * </p>
     * <p>
     * 以第一个set为标准
     * </p>
     *
     * @param keys
     *            可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sdiff(keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取set中的差集并存入到另一个key中
     * </p>
     * <p>
     * 以第一个set为标准
     * </p>
     *
     * @param dstkey
     *            差集存入的key
     * @param keys
     *            可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sdiffstore(dstkey, keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取指定set中的交集
     * </p>
     *
     * @param keys
     *            可以使一个string 也可以是一个string数组
     * @return
     */
    public Set<String> sinter(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sinter(keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取指定set中的交集 并将结果存入新的set中
     * </p>
     *
     * @param dstkey
     * @param keys
     *            可以使一个string 也可以是一个string数组
     * @return
     */
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sinterstore(dstkey, keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key返回所有set的并集
     * </p>
     *
     * @param keys
     *            可以使一个string 也可以是一个string数组
     * @return
     */
    public Set<String> sunion(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sunion(keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key返回所有set的并集,并存入到新的set中
     * </p>
     *
     * @param dstkey
     * @param keys
     *            可以使一个string 也可以是一个string数组
     * @return
     */
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sunionstore(dstkey, keys);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key将set中的value移除并添加到第二个set中
     * </p>
     *
     * @param srckey
     *            需要移除的
     * @param dstkey
     *            添加的
     * @param member
     *            set中的value
     * @return
     */
    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.smove(srckey, dstkey, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取set中value的个数
     * </p>
     *
     * @param key
     * @return
     */
    public Long scard(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.scard(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key判断value是否是set中的元素
     * </p>
     *
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        Boolean res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.sismember(key, member);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取set中随机的value,不删除元素
     * </p>
     *
     * @param key
     * @return
     */
    public String srandmember(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.srandmember(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }

    /**
     * <p>
     * 通过key获取set中所有的value
     * </p>
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.smembers(key);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
        } finally {
            close(jedis);
        }
        return res;
    }
    
}
