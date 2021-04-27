package cn.darkjrong.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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


}
