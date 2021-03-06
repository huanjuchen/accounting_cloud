package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.BankAccountVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 银行日记账
 *
 * @author HuanJu
 * @date 2020/8/8 17:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BankAccount implements Serializable {

    private Integer id;
    /**
     * 日期
     */
    private Date date;
    /**
     * 凭证号
     */
    private Integer proofId;
    /**
     * 摘要
     */
    private String abstraction;

    private Integer subjectId;
    /**
     * 对方科目
     */
    private Subject subject;
    /**
     * 借方金额
     */
    private BigDecimal debitMoney;

    /**
     * 贷方金额
     */
    private BigDecimal creditMoney;
    /**
     * 余额
     */
    private BigDecimal money;


}
