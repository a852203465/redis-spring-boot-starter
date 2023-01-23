package cn.darkjrong.redis.configuration.serializer;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Redis Key 默认序列化器
 *
 * @author Rong.Jia
 * @date 2023/01/23
 */
public class DefaultRedisValueSerializer extends Jackson2JsonRedisSerializer<Object> implements RedisValueSerializer<Object> {

    public DefaultRedisValueSerializer(Class<Object> type) {
        super(type);
    }
}
