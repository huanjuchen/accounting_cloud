package xyz.huanju.accounting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

/**
 * @author HuanJu
 * @date 2020/8/10 0:28
 */
@MapperScan("xyz.huanju.accounting.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class SubjectServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(SubjectServiceMain.class);
    }

}
