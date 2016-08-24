package ru.rabbit.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by rakhmetov on 24/08/16.
 */
@Service
public class RedisService {

    @Value("${redis.store.timeout}")
    private int keyExpirationTime;

    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String saveInteger(Integer num) {
        String key = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(key, num);
        redisTemplate.expire(key, 30, TimeUnit.SECONDS);
        return key;
    }

    public Integer getInteger(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
