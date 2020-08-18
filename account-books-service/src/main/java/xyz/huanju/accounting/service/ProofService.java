package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.ProofItem;

/**
 * @author HuanJu
 * @date 2020/8/19 2:01
 */

public interface ProofService {

    /**
     * 获取凭证
     *
     * @param id id
     * @return proof
     */
    Proof getProof(Integer id);


    /**
     * 更新凭证
     *
     * @param proof proof
     */
    void update(Proof proof);

    /**
     * update proof item
     *
     * @param proofItem proof item
     */
    void updateItem(ProofItem proofItem);
}
