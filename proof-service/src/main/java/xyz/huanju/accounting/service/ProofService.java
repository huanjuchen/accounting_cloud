package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.ProofItem;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 1:46
 */
public interface ProofService {

    /**
     * 保存凭证
     *
     * @param proof proof
     * @param tokenId tokenId
     */
    void save(Proof proof, String tokenId);

    /**
     * 查找凭证
     *
     * @param id 凭证id
     * @return proof
     */
    Proof find(Integer id);

    /**
     * 根据条件查找凭证
     *
     * @param map 条件
     * @return proof
     */
    List<Proof> list(Map<String, Object> map);

    /**
     * 查找满足条件的凭证数
     *
     * @param map 条件
     * @return count
     */
    Integer count(Map<String, Object> map);

    /**
     * 验证凭证
     *
     * @param proofId proof id
     * @param result result
     * @param tokenId tokenId
     */
    void verify(Integer proofId, Boolean result, String tokenId);

    /**
     * 凭证冲账
     * @param proofId proof id
     * @param tokenId token id
     */
    void trashProof(Integer proofId,String tokenId);


    /**
     * 更新凭证状态
     *
     * @param proof proof
     */
    void update(Proof proof);


    /**
     * 更新凭证 item
     *
     * @param proofItem proof item
     */
    void updateItem(ProofItem proofItem);


}
