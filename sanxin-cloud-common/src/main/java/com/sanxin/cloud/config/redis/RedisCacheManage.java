package com.sanxin.cloud.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author hifeng
 * @date 2018/7/30 14:58
 */
@Component
public class RedisCacheManage {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 写入缓存
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public void set(String key, Object value) throws Exception {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 写入缓存并设置时效
     *
     * @param key   key
     * @param value value
     * @param time  time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return boolean
     */
    public void set(String key, Object value, long time) throws Exception {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return Object
     */
    public Object get(String key) throws Exception {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除
     *
     * @param key key
     */
    public void delete(String key) throws Exception {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key key
     * @return boolean
     */
    public boolean exists(String key) throws Exception {
        return redisTemplate.hasKey(key);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return boolean
     */
    public void expire(String key, long time) throws Exception {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }
}
