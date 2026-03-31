package com.example.JournalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, Long ttlSeconds) {
        try {
            log.info("Redis SET key={} ttl={}s", key, ttlSeconds);
            redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
            log.info("Redis SET success for key={}", key);
        } catch (Exception e) {
            log.error("Redis SET failed for key={} | Error: {}", key, e.getMessage(), e);
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            log.info("Redis GET key={}", key);
            Object value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                log.info("Redis GET key={} → MISS (null)", key);
                return null;
            }
            log.info("Redis GET key={} → HIT", key);
            return clazz.cast(value);
        } catch (Exception e) {
            log.error("Redis GET failed for key={} | Error: {}", key, e.getMessage(), e);
            return null;
        }
    }
}