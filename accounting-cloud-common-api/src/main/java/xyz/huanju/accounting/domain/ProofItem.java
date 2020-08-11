package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.ProofItemVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 凭证内容项实体
 *
 * @author HuanJu
 * @date 2020/8/8 17:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProofItem implements Serializable {

    private Integer id;
    /**
     * 摘要
     */
    private String abstraction;
    /**
     * 借方明细账科目ID
     */
    @NotNull(message = "借方明细账科目允许为空")
    private Integer debitSubSubjectId;
    /**
     * 借方明细账科目
     */
    private Subject debitSubSubject;
    /**
     * 贷方明细账科目ID
     */
    @NotNull(message = "贷方明细账科目不允许为空")
    private Integer creditSubSubjectId;
    /**
     * 贷方明细账科目
     */
    private Subject creditSubSubject;
    /**
     * 借方总账科目ID
     */
    @NotNull(message = "借方总账科目不允许为空")
    private Integer debitLedgerSubjectId;
    /**
     * 借方总账科目
     */
    private Subject debitLedgerSubject;
    /**
     * 贷方总账科目ID
     */
    @NotNull(message = "贷方总账科目不允许为空")
    private Integer creditLedgerSubjectId;
    /**
     * 贷方总账 科目
     */
    private Subject creditLedgerSubject;
    /**
     * 金额
     */
    @NotNull(message = "金额不允许为空")
    private BigDecimal money;
    /**
     * 记账标识
     */
    private Boolean charge;
    /**
     * 关联列，凭证号
     */
    private Integer proofId;



}
