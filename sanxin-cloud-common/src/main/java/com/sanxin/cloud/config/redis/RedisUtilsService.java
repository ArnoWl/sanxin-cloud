package com.sanxin.cloud.config.redis;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;



@Service
public class RedisUtilsService {
	protected final static Logger logger = LoggerFactory.getLogger(RedisUtilsService.class);
	@Autowired
	private volatile RedisTemplate redisTemplate;

	/**
	 * 秒级内做并发处理
	 * @param key
	 * @param cacheSeconds
	 * @return true 表示可以继续下面逻辑没有并发  false表示并发数据
	 */
	public  boolean setIncrSecond(String key, long cacheSeconds) {
		try {
			setStringSerializer(redisTemplate);
			long count=redisTemplate.opsForValue().increment(key, 1);
			 //此段代码出现异常则会出现死锁问题，key一直都存在
			 if(count == 1){
				 //设置有效期X秒
				 redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
				 return true;
			 }
			 //如果存在表示重复
			 return false;
		 } catch (Exception e) {
			 logger.error("redis加锁异常", e);
			 redisTemplate.delete(key);		//出现异常删除锁
			 return true;
		 }
	}

	 /**
     * 获取唯一Id
     * @param key
     * @param hashKey
     * @param delta 增加量（不传采用1）
     * @return
     */
    public Long incrementHash(String key,String hashKey,Long delta){
        try {
            if (null == delta) {
                delta=1L;
            }
            setStringSerializer(redisTemplate);
            return redisTemplate.opsForHash().increment(key, hashKey, delta);
        } catch (Exception e) {//redis宕机时采用uuid的方式生成唯一id
            int first = new Random(10).nextInt(8) + 1;
            int randNo=UUID.randomUUID().toString().hashCode();
            if (randNo < 0) {
                randNo=-randNo;
            }
            return Long.valueOf(first + String.format("%16d", randNo));
        }
    }

    /**
     * 设置无限期 键值对信息
     * @param key
     * @param value
     */
    public void setKey(String key,String value) {
    		setStringSerializer(redisTemplate);
    		redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 设置有限期 键值对信息
     * @param key
     * @param value
     * @param second 秒
     */
    public void setKey(String key,String value,long second) {
    		setStringSerializer(redisTemplate);
		redisTemplate.opsForValue().set(key,value,second,TimeUnit.SECONDS);
    }


    /**
     * 根据键获取值
     * @param key
     * @return
     */
    public String getKey(String key) {
		setStringSerializer(redisTemplate);
		if(StringUtils.isEmpty(key)){
			return "";
		}
		String val=String.valueOf(redisTemplate.opsForValue().get(key));
		return val;
    }

    /**
     * 根据键获取值
     * @param key
     * @return
     */
    public Set<String> getKeys(String key) {
		setStringSerializer(redisTemplate);
		Set<String> val=redisTemplate.keys(key);
		return val;
    }

    /**
     * 批量删除
     * @param keys
     */
    public void deleteKey(Set<String> keys) {
		for(String k:keys) {
			redisTemplate.delete(k);
		}
    }

	/**
	 * 删除key
	 * @param key
	 */
	public void deleteKey(String key) {
		 setStringSerializer(redisTemplate);
		 redisTemplate.delete(key);
	}

	private void setStringSerializer(RedisTemplate<String, String> template) {
		RedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setDefaultSerializer(stringRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
    }


}
