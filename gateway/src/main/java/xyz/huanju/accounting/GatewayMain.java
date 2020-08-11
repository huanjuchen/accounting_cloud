package xyz.huanju.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author HuanJu
 * @date 2020/8/10 19:14
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class);
    }

}
