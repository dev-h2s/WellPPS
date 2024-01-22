package com.wellnetworks.wellsecure.java.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean isConnected() {
        try {
            String pingResponse = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().ping();

            return "PONG".equals(pingResponse);
        } catch (Exception e) {
            return false;
        }
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}