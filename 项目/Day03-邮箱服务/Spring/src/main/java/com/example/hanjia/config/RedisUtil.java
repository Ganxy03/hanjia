package com.example.hanjia.config;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import java.util.UUID;

@Component
public class RedisUtil {
    private static final String REDIS_HOST = "124.221.138.245";
    private static final int REDIS_PORT = 6379;
    private static final String REDIS_PASSWORD = "";
    private static Jedis jedis;

    static {
        jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        jedis.auth(REDIS_PASSWORD);
    }
    public static void selectDatabase(int databaseIndex) {
        jedis.select(databaseIndex);
    }
    public static void set(String key, String value, int expireSeconds) {
        jedis.setex(key, expireSeconds, value);
    }
    public static String get(String key) {
        return jedis.get(key);
    }
    public static void delete(String key) {
        jedis.del(key);
    }

    public static String generateToken(String username) {
        String token = UUID.randomUUID().toString(); // 生成随机token值
        jedis.setex(username, 1800, token); // 设置token的过期时间为30分钟（1800秒），同时将token与username关联存入Redis
        return token;
    }
}
