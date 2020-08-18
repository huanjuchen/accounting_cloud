package xyz.huanju.accounting.dao;

import org.apache.ibatis.annotations.Param;
import xyz.huanju.accounting.domain.LedgerAccount;
import xyz.huanju.accounting.domain.OpeningBalance;

import java.util.Date;

/**
 * @author HuanJu
 * @date 2020/8/17 0:41
 */
public interface LedgerAccountDAO extends BaseDAO<Integer, LedgerAccount> {

    /**
     * 根据科目和日期查找总账
     *
     * @param subjectId subject id
     * @param date      date
     * @return ledger account
     */
    LedgerAccount findBySubjectAndDate(@Param("subjectId") Integer subjectId, @Param("date") Date date);

    /**
     * 根据科目 日期获取期初余额
     *
     * @param date      日期
     * @param subjectId subject id
     * @return sum money
     */
    OpeningBalance getOpeningBalance(@Param("date") String date, @Param("subjectId") Integer subjectId);


}
