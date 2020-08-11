package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.LedgerAccountVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/8 17:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LedgerAccount implements Serializable {

    private Integer id;
    /**
     * 科目ID
     */
    private Integer subjectId;
    /**
     * 科目
     */
    private Subject subject;
    /**
     * 日期
     */
    private Date date;
    /**
     * 摘要
     */
    private String abstraction;
    /**
     * 借方金额
     */
    private BigDecimal debitMoney;
    /**
     * 贷方金额
     */
    private BigDecimal creditMoney;

    /**
     * 借贷平标志
     * 借: -1
     * 贷: 1
     * 平: 0
     */
    private Integer mark;

    /**
     * 金额
     */
    private BigDecimal money;

}
