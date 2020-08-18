package xyz.huanju.accounting.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author HuanJu
 * @date 2020/8/15 1:37
 */
@Configuration
public class RabbitConfig {


    private String queueName;

    private String exchangeName;

    @Value("${mq.config.proof.queue-name}")
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Value("${mq.config.proof.exchange-name}")
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(this.queueName).build();
    }

    @Bean
    public FanoutExchange msgExchange() {
        return ExchangeBuilder.fanoutExchange(this.exchangeName).durable(true).build();
    }


    @Bean
    @Resource
    public Binding proofBinding(Queue queue,FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }


}
