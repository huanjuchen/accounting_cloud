package xyz.huanju.accounting.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.huanju.accounting.domain.OpeningBalance;
import xyz.huanju.accounting.domain.SubAccount;

/**
 * @author HuanJu
 * @date 2020/8/16 17:23
 */
public interface SubAccountMapper extends BaseMapper<Integer,SubAccount> {

    /**
     * 根据科目 日期获取期初余额
     *
     * @param date      日期
     * @param subjectId subject id
     * @return sum money
     */
    OpeningBalance getOpeningBalance(@Param("date") String date, @Param("subjectId") Integer subjectId);


}
