package xyz.huanju.accounting.mapper;

import xyz.huanju.accounting.domain.CashAccount;
import xyz.huanju.accounting.domain.OpeningBalance;

/**
 * @author HuanJu
 * @date 2020/8/16 17:21
 */
public interface CashAccountMapper extends BaseMapper<Integer,CashAccount> {

    /**
     * 计算初始余额
     *
     * @param date 日期
     * @return sum money
     */
    OpeningBalance getOpeningBalance(String date);

}
