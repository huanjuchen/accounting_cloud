package xyz.huanju.accounting.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * @author HuanJu
 * @date 2020/8/9 23:08
 */
@Configuration
public class ValidatorConfiguration {


    @Bean
    public Validator validator(){
        ValidatorFactory factory= Validation.byProvider(HibernateValidator.class)
                .configure()
                //快速失败模式
                .addProperty("hibernate.validator.fail_fast","true")
                .buildValidatorFactory();
        return factory.getValidator();
    }

}
