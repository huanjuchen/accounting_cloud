package xyz.huanju.accounting.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import xyz.huanju.accounting.domain.Subject;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author HuanJu
 * @date 2020/8/18 22:23
 */
@Configuration
public class RedisCacheConfig {

    private String host;

    private int port;

    private int db;

    @Value("${spring.redis.host}")
    public void setHost(String host) {
        this.host = host;
    }

    @Value("${spring.redis.port}")
    public void setPort(int port) {
        this.port = port;
    }

    @Value("${spring.redis.database}")
    public void setDb(int db) {
        this.db = db;
    }

    /**
     * 配置Redis连接工厂
     *
     * @return Lettuce连接工厂
     */
    @Bean(name = "subjectCacheConnectFactory")
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(this.host);
        configuration.setPort(this.port);
        configuration.setDatabase(this.db);
        LettuceConnectionFactory lcf = new LettuceConnectionFactory(configuration);
        return lcf;
    }


    /**
     * 缓存Redis 模板
     *
     * @param cf 连接工厂
     * @return redis模板
     */
    @Bean("subjectCacheTemplate")
    public RedisTemplate<String, Subject> subjectCacheTemplate(
            @Qualifier("subjectCacheConnectFactory") RedisConnectionFactory cf) {
        RedisSerializer<String> ks = new StringRedisSerializer();
        RedisSerializer<Subject> vs = new Jackson2JsonRedisSerializer<>(Subject.class);
        RedisTemplate<String, Subject> rt = new RedisTemplate<>();
        rt.setKeySerializer(ks);
        rt.setValueSerializer(vs);
        rt.setConnectionFactory(cf);
        rt.afterPropertiesSet();
        return rt;
    }

    @Bean("cacheManager")
    public CacheManager cacheManager(@Qualifier("subjectCacheConnectFactory") RedisConnectionFactory cf) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        RedisSerializer<String> ks = new StringRedisSerializer();
        RedisSerializer<Subject> vs = new Jackson2JsonRedisSerializer<>(Subject.class);
        configuration = configuration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(ks));
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(vs));
        configuration.usePrefix();
        configuration = configuration.entryTtl(Duration.ofHours(12L));
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf);
        builder.cacheDefaults(configuration);
        Set<String> set = new HashSet<>();
        set.add("subjectCache");
        builder.initialCacheNames(set);
        RedisCacheManager cacheManager = builder.build();
        cacheManager.afterPropertiesSet();
        return cacheManager;
    }


}
