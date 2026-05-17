package com.inviq.conn;

import com.inviq.constant.RedisEnvConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;

/**
 * @author ripesh created on 13/05/2026
 */
@Configuration
public class RedisConfig {

  @Bean
  public RedisClient jedisPooled() {
    ConnectionPoolConfig poolConfig = new ConnectionPoolConfig();
    poolConfig.setMaxTotal(50);
    poolConfig.setMaxIdle(10);
    poolConfig.setMinIdle(2);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestWhileIdle(true);

    DefaultJedisClientConfig clientConfig =
        DefaultJedisClientConfig.builder()
            .password(System.getenv(RedisEnvConstant.REDIS_PASSWORD))
            .timeoutMillis(2000)
            .build();

    return RedisClient.builder()
        .hostAndPort(
            System.getenv(RedisEnvConstant.REDIS_HOST),
            Integer.parseInt(System.getenv(RedisEnvConstant.REDIS_PORT)))
        .clientConfig(clientConfig)
        .poolConfig(poolConfig)
        .build();
  }
}
