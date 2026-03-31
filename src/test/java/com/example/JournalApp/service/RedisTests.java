package com.example.JournalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

//    NOTE: redis ke serializer-deserializer alag hai and
//    Spring boot ke alag hai (mtlb redis cli me jo set hoga wo spring me get nhi hoga vice-verca true)

//    hence hum ek bean banayenge spring ke serializer-deserializer set karne ke liye
//     here i am making in config of app
    @Disabled
    @Test
    void testSendMail(){
        // redisTemplate.opsForValue().set("email","shikshak@gmail.com");
        Object mail = redisTemplate.opsForValue().get("email");
        int a = 1;
    }
//    checking github
}
