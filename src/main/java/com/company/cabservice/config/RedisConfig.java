package com.company.cabservice.config;

import com.company.cabservice.config.RedisConfig.RegisteredvehiclesIndexConfiguration;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.index.IndexConfiguration;
import org.springframework.data.redis.core.index.IndexDefinition;
import org.springframework.data.redis.core.index.SimpleIndexDefinition;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@EnableRedisRepositories(basePackages = "com.company.cabservice.repository.redis", indexConfiguration = RegisteredvehiclesIndexConfiguration.class)
@PropertySource("classpath:application.yml")
public class RedisConfig {

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {

    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
    return template;
  }

  public static class RegisteredvehiclesIndexConfiguration extends IndexConfiguration {

    @Override
    protected Iterable<IndexDefinition> initialConfiguration() {
      return Collections.singleton(new SimpleIndexDefinition("registered-vehicles", "cabType"));
    }
  }

}