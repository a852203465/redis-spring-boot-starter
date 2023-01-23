package cn.darkjrong.redis.configuration.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Redis Key 默认序列化器
 *
 * @author Rong.Jia
 * @date 2023/01/23
 */
public class DefaultRedisValueSerializer extends Jackson2JsonRedisSerializer<Object> implements RedisValueSerializer<Object> {

    public DefaultRedisValueSerializer() {
        super(Object.class);
    }

    /**
     * set 对象映射器
     */
    public void setObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        this.setObjectMapper(om);
    }


}
