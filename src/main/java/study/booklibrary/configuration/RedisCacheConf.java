package study.booklibrary.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import study.booklibrary.configuration.property.BookCacheProperty;
import study.booklibrary.configuration.property.ExpiryProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
@EnableConfigurationProperties(BookCacheProperty.class)
public class RedisCacheConf {

    @Bean
    public CacheManager redisCacheManger(BookCacheProperty property, LettuceConnectionFactory factory) {
        RedisCacheConfiguration configurationDefault = RedisCacheConfiguration.defaultCacheConfig();

        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

        Map<String, ExpiryProperty> caches = property.getCaches();
        List<String> cacheNames = property.getCacheNames();

        for (String cacheName : cacheNames) {
            redisCacheConfigurationMap.put(
                    cacheName,
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(caches.get(cacheName).getExpiry()));
        }

        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(configurationDefault)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }



}
