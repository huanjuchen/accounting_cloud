package xyz.huanju.accounting.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.huanju.accounting.domain.mq.ProofMsg;
import xyz.huanju.accounting.mq.callback.ProofMsgReturnCallback;
import xyz.huanju.accounting.service.ProofMqService;
import xyz.huanju.accounting.utils.JsonUtils;


/**
 * @author HuanJu
 * @date 2020/8/13 19:50
 */
@Service("proofMqService")
@Slf4j
public class RabbitProofMqServiceImpl implements ProofMqService, InitializingBean {

    private RabbitTemplate rabbitTemplate;

    private String exchangeName;

    private final MessagePostProcessor msgPostProcessor = new ProofMessageProcess();


    private ProofMsgReturnCallback proofMsgReturnCallback;



    @Value("${mq.config.proof.exchange-name}")
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }


    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Autowired
    public void setProofMsgReturnCallback(ProofMsgReturnCallback proofMsgReturnCallback) {
        this.proofMsgReturnCallback = proofMsgReturnCallback;
    }



    @Override
    public void sendMsg(ProofMsg proofMsg) {
        rabbitTemplate.convertAndSend(exchangeName, null, JsonUtils.toJson(proofMsg), msgPostProcessor);
    }


    @Override
    public void afterPropertiesSet() {
        rabbitTemplate.setReturnCallback(proofMsgReturnCallback);

    }


    static class ProofMessageProcess implements MessagePostProcessor {

        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            MessageProperties properties = message.getMessageProperties();
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        }

        @Override
        public Message postProcessMessage(Message message, Correlation correlation) {
            return message;
        }
    }

}
