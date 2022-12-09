package com.onefly.united.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ClassUtils;

/**
 * Redis配置
 *
 * @author Mark Rundon
 */
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfig {

    /**
     * 系统自带的RedisTemplate<Object,Object>不方便后续编码，自定义成<String,Object>
     *
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public Config config(RedisProperties redisProperties) throws Exception {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setDatabase(redisProperties.getDatabase())
                .setTimeout((int) redisProperties.getTimeout().toMillis())
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(64)
                .setDnsMonitoringInterval(5000)
                .setSubscriptionConnectionMinimumIdleSize(1)
                .setSubscriptionConnectionPoolSize(50)
                .setSubscriptionsPerConnection(5)
                .setRetryAttempts(3)
                .setRetryInterval(1500)
                .setTimeout(3000)
                .setConnectTimeout(10000)
                .setIdleConnectionTimeout(10000);
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        Codec codec = (Codec) ClassUtils.forName("org.redisson.codec.JsonJacksonCodec", ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        config.setThreads(4);
        config.setEventLoopGroup(new NioEventLoopGroup());
        return config;
    }

    @Bean
    RedissonClient redissonClient(Config config) {
        return Redisson.create(config);
    }

}