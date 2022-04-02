package com.coderhouse.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@RequiredArgsConstructor
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class})
public class RedisConfiguration {

    private final AppProperties redisProperties;

    //singleton
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        var config = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        return new JedisConnectionFactory(config);
    }

    //singleton
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        var redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
