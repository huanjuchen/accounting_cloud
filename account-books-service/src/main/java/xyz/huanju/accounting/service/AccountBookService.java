package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.*;
import xyz.huanju.accounting.domain.mq.ProofMsg;


import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/17 15:14
 */
public interface AccountBookService {

    /**
     * 获取银行日记账
     *
     * @param startDate start date
     * @param endDate   end date
     * @param page      page
     * @return bank account list
     */
    List<BankAccount> getBankAccount(String startDate, String endDate, Integer page);

    /**
     * 获取现金日记账
     *
     * @param startDate start date
     * @param endDate   end date
     * @param page      page
     * @return cash account list
     */
    List<CashAccount> getCashAccount(String startDate, String endDate, Integer page);

    /**
     * 获取明细分类账
     *
     * @param subjectId subject id
     * @param startDate start date
     * @param endDate   end date
     * @param page      page
     * @return sub account list
     */
    List<SubAccount> getSubAccount(Integer subjectId, String startDate, String endDate, Integer page);

    /**
     * 获取总账
     *
     * @param subjectId subject id
     * @param startDate start date
     * @param endDate   end date
     * @return ledger account list
     */
    List<LedgerAccount> getLedgerAccount(Integer subjectId, String startDate, String endDate);

    /**
     * 记账处理
     *
     * @param proofMsg proof msg
     */
    void accountBookHandle(ProofMsg proofMsg);


    /**
     * 获取时间范围内的银行日记账记录数
     *
     * @param startDate start date
     * @param endDate   end date
     * @return count
     */
    Integer getBankAccountCount(String startDate, String endDate);

    /**
     * 获取时间范围内的现金日记账记录数
     *
     * @param startDate start date
     * @param endDate   end date
     * @return count
     */
    Integer getCashAccountCount(String startDate, String endDate);

    /**
     * 获取时间范围内满足条件的总账记录数
     *
     * @param subjectId subject id
     * @param startDate start date
     * @param endDate   end date
     * @return count
     */
    Integer getLedgerAccountCount(Integer subjectId, String startDate, String endDate);

    /**
     * 获取时间范围内满足条件的明细账记录数
     *
     * @param subjectId subject id
     * @param startDate start date
     * @param endDate   end date
     * @return count
     */
    Integer getSubAccountCount(Integer subjectId, String startDate, String endDate);


}
