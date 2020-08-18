package xyz.huanju.accounting.dao;

import xyz.huanju.accounting.domain.CashAccount;
import xyz.huanju.accounting.domain.OpeningBalance;

/**
 * @author HuanJu
 * @date 2020/8/17 0:37
 */
public interface CashAccountDAO extends BaseDAO<Integer, CashAccount> {

    /**
     * 计算初始余额
     *
     * @param date 日期
     * @return sum money
     */
    OpeningBalance getOpeningBalance(String date);

}
