package xyz.huanju.accounting.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuanJu
 * @date 2020/8/18 17:05
 */
@Configuration
@MapperScan("xyz.huanju.accounting.mapper")
public class MyBatisConfig {
}
