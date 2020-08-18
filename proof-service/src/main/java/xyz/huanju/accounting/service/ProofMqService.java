package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.mq.ProofMsg;

/**
 * @author HuanJu
 * @date 2020/8/13 19:13
 */
public interface ProofMqService {

    /**
     * 发送消息
     * @param proofMsg 待处理凭证信息
     */
    void sendMsg(ProofMsg proofMsg);

}
