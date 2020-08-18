package xyz.huanju.accounting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.huanju.accounting.domain.mq.ProofMsg;
import xyz.huanju.accounting.service.ProofMqService;

import java.util.Date;

/**
 * @author HuanJu
 * @date 2020/8/13 23:36
 */
@SpringBootTest
public class RabbitMqTest {


    private ProofMqService proofMqService;

    @Autowired
    @Qualifier("proofMqService")
    public void setProofMqService(ProofMqService proofMqService) {
        this.proofMqService = proofMqService;
    }



    @Test
    public void sendTest(){
        ProofMsg proofMsg=new ProofMsg("1",1,1,new Date());
        proofMqService.sendMsg(proofMsg);
    }


}
