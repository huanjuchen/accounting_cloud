package xyz.huanju.accounting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import xyz.huanju.accounting.domain.token.Token;

/**
 * @author HuanJu
 * @date 2020/8/9 15:50
 */
@Configuration
public class TokenRedisConfig {

    @Value("${token.redis.host}")
    private String host;

    @Value("${token.redis.port}")
    private int port;

    @Value("${token.redis.db}")
    private int db;


    private LettuceConnectionFactory tokenConnFactory(){
        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
        configuration.setHostName(this.host);
        configuration.setPort(this.port);
        configuration.setDatabase(this.db);
        LettuceConnectionFactory factory=new LettuceConnectionFactory(configuration);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean(name = "tokenRedisTemplate")
    public RedisTemplate<String, Token> tokenTemplate(){
        RedisSerializer<String> keySerializer=new StringRedisSerializer();
        RedisSerializer<Token> valueSerializer=new Jackson2JsonRedisSerializer<>(Token.class);
        RedisTemplate<String,Token> template=new RedisTemplate<>();
        template.setConnectionFactory(tokenConnFactory());
        template.setValueSerializer(valueSerializer);
        template.setKeySerializer(keySerializer);
        template.afterPropertiesSet();
        return template;
    }




}
