package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.CashAccountVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 现金日记账
 * @author HuanJu
 * @date 2020/8/8 17:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CashAccount implements Serializable {

    private Integer id;

    private Date date;

    private Integer proofId;

    private String abstraction;

    private Integer subjectId;

    private Subject subject;

    private BigDecimal debitMoney;

    private BigDecimal creditMoney;

    private BigDecimal money;

}
