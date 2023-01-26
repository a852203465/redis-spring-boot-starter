package cn.darkjrong.redis;

import cn.darkjrong.redis.configuration.RedisConfiguration;
import cn.darkjrong.redis.configuration.serializer.DefaultRedisKeySerializer;
import cn.darkjrong.redis.configuration.serializer.DefaultRedisValueSerializer;
import cn.darkjrong.redis.configuration.serializer.RedisKeySerializer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RedisInitTest {

    protected static RedisUtils redisUtils;

    @BeforeEach
    public void initRedis() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setDatabase(4);

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        CacheProperties cacheProperties = getCacheProperties();
        RedisKeySerializer redisKeySerializer = new DefaultRedisKeySerializer();
        DefaultRedisValueSerializer redisValueSerializer = new DefaultRedisValueSerializer();
        redisValueSerializer.setObjectMapper();

        RedisConfiguration redisConfiguration = new RedisConfiguration(lettuceConnectionFactory, cacheProperties,
                redisKeySerializer, redisValueSerializer);

        RedisTemplate<String, Object> redisTemplate = redisConfiguration.redisTemplate(lettuceConnectionFactory);
        redisUtils = redisConfiguration.redisUtils(redisTemplate);

    }

    private static CacheProperties getCacheProperties() {
        CacheProperties cacheProperties = new CacheProperties();
        cacheProperties.setType(CacheType.REDIS);
        cacheProperties.getRedis().setTimeToLive(Duration.ofSeconds(60));
        return cacheProperties;
    }































}
