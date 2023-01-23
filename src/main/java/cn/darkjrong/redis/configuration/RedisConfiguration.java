package cn.darkjrong.redis.configuration;

import cn.darkjrong.redis.RedisUtils;
import cn.darkjrong.redis.configuration.serializer.RedisKeySerializer;
import cn.darkjrong.redis.configuration.serializer.RedisValueSerializer;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Redis 配置
 *
 * @author Rong.Jia
 * @date 2019/01/14 17:20
 */
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

    private final Duration timeToLive = Duration.ofSeconds(60);
    private final RedisConnectionFactory redisConnectionFactory;
    private final CacheProperties cacheProperties;
    private final RedisKeySerializer redisKeySerializer;
    private final RedisValueSerializer<?> redisValueSerializer;

    public RedisConfiguration(RedisConnectionFactory redisConnectionFactory,
                              CacheProperties cacheProperties,
                              RedisKeySerializer redisKeySerializer,
                              RedisValueSerializer<?> redisValueSerializer) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.cacheProperties = cacheProperties;
        this.redisKeySerializer = redisKeySerializer;
        this.redisValueSerializer = redisValueSerializer;
    }

    /**
     * 在没有指定缓存Key的情况下，key生成策略
     *
     * @return {@link KeyGenerator} key生成策略
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 缓存管理器
     *
     * @return {@link CacheManager} 缓存管理器
     */
    @Bean
    @Primary
    @Override
    public CacheManager cacheManager() {

        //关键点，spring cache的注解使用的序列化都从这来，没有这个配置的话使用的jdk自己的序列化，实际上不影响使用，只是打印出来不适合人眼识别
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()

                //key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisKeySerializer))

                //value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer))

                .disableCachingNullValues()

                //缓存过期时间
                .entryTtl((cacheProperties.getRedis() == null
                        || cacheProperties.getRedis().getTimeToLive() == null
                        || cacheProperties.getRedis().getTimeToLive().isZero())
                        ? timeToLive : cacheProperties.getRedis().getTimeToLive());

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(config)
                .transactionAware();

        return builder.build();
    }

    /**
     * RedisTemplate配置 在单独使用redisTemplate的时候 重新定义序列化方式
     *
     * @param redisConnectionFactory 连接工厂
     * @return RedisTemplate<String, Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(redisValueSerializer);
        template.setHashValueSerializer(redisValueSerializer);

        template.setHashKeySerializer(redisKeySerializer);

        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(redisKeySerializer);

        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 注入封装RedisTemplate
     *
     * @param redisTemplate {@link RedisTemplate}
     * @return {@link RedisUtils}
     */
    @Bean(name = "redisUtils")
    public RedisUtils redisUtils(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils redisUtils = new RedisUtils();
        redisUtils.setRedisTemplate(redisTemplate);
        return redisUtils;
    }

}
