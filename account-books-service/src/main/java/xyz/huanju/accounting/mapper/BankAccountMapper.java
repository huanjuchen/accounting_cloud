package xyz.huanju.accounting.mapper;

import xyz.huanju.accounting.domain.BankAccount;
import xyz.huanju.accounting.domain.OpeningBalance;

/**
 * @author HuanJu
 * @date 2020/8/16 17:16
 */
public interface BankAccountMapper extends BaseMapper<Integer,BankAccount> {

    /**
     * 计算初始金额
     *
     * @param date 日期
     * @return sum money
     */
    OpeningBalance getOpeningBalance(String date);

}
