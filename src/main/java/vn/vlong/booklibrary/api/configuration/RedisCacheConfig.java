package vn.vlong.booklibrary.api.configuration;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@EnableCaching
@Configuration
@Slf4j
public class RedisCacheConfig extends CachingConfigurerSupport {

  @Autowired
  private RedisProperties redisProperties;

  public LettuceConnectionFactory redisConnectionFactory() {

    RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
        .master(redisProperties.getSentinel().getMaster());
    redisProperties.getSentinel().getNodes().forEach(s -> {
      String[] part = s.split(":");
      sentinelConfig.sentinel(part[0], Integer.valueOf(part[1]));
    });
    sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
    sentinelConfig.setSentinelPassword(RedisPassword.of(redisProperties.getPassword()));

    return new LettuceConnectionFactory(sentinelConfig);
  }

  @Bean
  public RedisCacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory) {
    CacheKeyPrefix cacheNamePrefix = cacheName -> "myPrefix::" + cacheName + "::";

    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .disableCachingNullValues()
        .computePrefixWith(cacheNamePrefix)
        //.disableKeyPrefix()
        .entryTtl(Duration.ofMinutes(1));

    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
        .cacheDefaults(redisCacheConfiguration)
        .build();
  }

  @Override
  public CacheErrorHandler errorHandler() {
    return new SimpleCacheErrorHandler() {
      @Override
      public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.warn(
            String.format("CacheGetError %s:%s:%s", cache.getName(), key, exception.getMessage()),
            exception);
      }

      @Override
      public void handleCachePutError(RuntimeException exception, Cache cache, Object key,
          Object value) {
        log.warn(
            String.format("CachePutError %s:%s:%s", cache.getName(), key, exception.getMessage()),
            exception);
      }

      @Override
      public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.warn(
            String.format("CacheEvictError %s:%s:%s", cache.getName(), key, exception.getMessage()),
            exception);
      }

      @Override
      public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.warn(String.format("CacheClearError %s:%s", cache.getName(), exception.getMessage()),
            exception);
      }
    };
  }
}
