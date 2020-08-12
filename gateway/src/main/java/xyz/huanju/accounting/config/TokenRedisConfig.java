package xyz.huanju.accounting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import xyz.huanju.accounting.domain.token.Token;

import javax.annotation.Resource;

/**
 * @author HuanJu
 * @date 2020/8/12 23:44
 */
@Configuration
public class TokenRedisConfig {

    @Value("${token.redis.host}")
    private String host;

    @Value("${token.redis.port}")
    private Integer port;

    @Value("${token.redis.db}")
    private Integer db;


    /**
     * 实例化Redis连接工厂
     */
    @Bean("lettuceConnectionFactory")
    public LettuceConnectionFactory lcFactory(){

        /*
        redis 单服务器配置
         */
        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setDatabase(db);
        configuration.setPort(port);
        /*
        lettuce 连接工厂
         */
        LettuceConnectionFactory factory=new LettuceConnectionFactory(configuration);
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * 实例化Redis模板
     */
    @Bean("tokenTemplate")
    @Autowired
    public RedisTemplate<String, Token> tokenRedisTemplate(
            @Qualifier("lettuceConnectionFactory") LettuceConnectionFactory factory){
        //实例化模板
        RedisTemplate<String,Token> template=new RedisTemplate<>();
        /*
        创建 key 和 value 序列化器
         */
        RedisSerializer<String> keySerializer=new StringRedisSerializer();
        RedisSerializer<Token> valSerializer=new Jackson2JsonRedisSerializer<>(Token.class);

        /*
        序列化器设置到模板
         */
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valSerializer);
        //添加连接工厂
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();

        //返回模板添加到容器
        return template;
    }



}
