package xyz.huanju.accounting.dao;

import xyz.huanju.accounting.domain.BankAccount;
import xyz.huanju.accounting.domain.OpeningBalance;

/**
 * @author HuanJu
 * @date 2020/8/17 0:35
 */
public interface BankAccountDAO extends BaseDAO<Integer, BankAccount> {

    /**
     * 计算初始金额
     *
     * @param date 日期
     * @return sum money
     */
    OpeningBalance getOpeningBalance(String date);


}
