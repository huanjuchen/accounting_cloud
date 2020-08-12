package xyz.huanju.accounting.dao;

import xyz.huanju.accounting.domain.ProofItem;

import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/11 0:19
 */
public interface ProofItemDAO extends BaseDAO<ProofItem> {

    /**
     * 获取所有凭证项
     *
     * @param proofId proof
     * @return proof item list
     */
    List<ProofItem> items(Integer proofId);


    /**
     * 根据科目获取 proof item
     *
     * @param subjectId subject id
     * @return proof item list
     */
    List<ProofItem> listBySubject(Integer subjectId);
}
