package cn.darkjrong.spring.boot.autoconfigure;

import cn.darkjrong.redis.configuration.serializer.DefaultRedisKeySerializer;
import cn.darkjrong.redis.configuration.serializer.DefaultRedisValueSerializer;
import cn.darkjrong.redis.configuration.serializer.RedisKeySerializer;
import cn.darkjrong.redis.configuration.serializer.RedisValueSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 自动配置类
 * @author Rong.Jia
 * @date 2021/01/28 16:18:22
 */
@Configuration
@ComponentScan("cn.darkjrong.redis")
@EnableConfigurationProperties(CacheProperties.class)
public class RedisSerializeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RedisKeySerializer.class)
    public RedisKeySerializer keySerializer() {
        return new DefaultRedisKeySerializer();
    }

    @Bean
    @ConditionalOnMissingBean(RedisValueSerializer.class)
    public RedisValueSerializer<?> valueSerializer() {
        DefaultRedisValueSerializer defaultRedisValueSerializer = new DefaultRedisValueSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        defaultRedisValueSerializer.setObjectMapper(om);
        return defaultRedisValueSerializer;
    }

}
