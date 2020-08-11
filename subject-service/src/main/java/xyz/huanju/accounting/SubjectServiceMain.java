package xyz.huanju.accounting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author HuanJu
 * @date 2020/8/10 0:28
 */
@MapperScan("xyz.huanju.accounting.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class SubjectServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(SubjectServiceMain.class);
    }

}
