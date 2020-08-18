package xyz.huanju.accounting.mq;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import xyz.huanju.accounting.domain.mq.ProofMsg;
import xyz.huanju.accounting.service.AccountBookService;
import xyz.huanju.accounting.utils.JsonUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;


/**
 * @author HuanJu
 * @date 2020/8/15 18:49
 */
@Component
@Slf4j
public class ProofRabbitConsumer {

    @Resource
    private AccountBookService accountBookService;


    @RabbitListener(queues = "${mq.config.proof.queue-name}", ackMode = "MANUAL")
    public void handleMsg(Message msg, Channel channel, @Payload String jsonStr, MessageHeaders headers) {
        ProofMsg proofMsg= JsonUtils.toObject(jsonStr,ProofMsg.class);
        accountBookService.accountBookHandle(proofMsg);
        if (channel != null) {
            try {
                if (msg!=null){
                    MessageProperties properties = msg.getMessageProperties();
                    channel.basicAck(properties.getDeliveryTag(),true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
