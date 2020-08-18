package xyz.huanju.accounting.mq.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Error;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 错误返回 CallBack
 *
 * @author HuanJu
 * @date 2020/8/14 22:48
 */
@Component
@Slf4j
public class ProofMsgReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        byte[] bytes = message.getBody();
        if (bytes != null && bytes.length > 0){
            String str=new String(bytes);
            log.error("返回代码 replyCode: {}", replyCode);
            log.error("返回信息 replyText: {}", replyText);
            log.error("未成功发送的处理信息 ProofMsg: {}", str);
        }
    }
}
