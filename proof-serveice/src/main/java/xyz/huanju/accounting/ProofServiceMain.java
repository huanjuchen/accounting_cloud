package xyz.huanju.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author HuanJu
 * @date 2020/8/10 23:57
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProofServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(ProofServiceMain.class);
    }
}
