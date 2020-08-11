package xyz.huanju.accounting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author HuanJu
 * @date 2020/8/9 1:42
 */

@MapperScan("xyz.huanju.accounting.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class UserCenterMain {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterMain.class);
    }

}
