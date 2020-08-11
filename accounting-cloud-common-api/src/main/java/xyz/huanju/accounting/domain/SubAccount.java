package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.SubAccountVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 明细账实体
 *
 * @author HuanJu
 * @date 2020/8/8 17:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SubAccount implements Serializable {

    private Integer id;
    /**
     * 科目Id
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
     * 凭证号
     */
    private Integer proofId;
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


    public SubAccountVO covert() {
        SubAccountVO vo = new SubAccountVO();
        vo.setId(this.id)
                .setSubject(this.subject == null ? null : this.subject.covert())
                .setDate(this.date)
                .setProofId(this.proofId)
                .setAbstraction(this.abstraction)
                .setDebitMoney(this.debitMoney)
                .setCreditMoney(this.creditMoney)
                .setMark(this.mark)
                .setMoney(this.money);
        return vo;
    }

    public static List<SubAccountVO> listCovert(List<SubAccount> sourcesList) {
        List<SubAccountVO> vos = new ArrayList<>(sourcesList.size());
        for (SubAccount subAccount : sourcesList) {
            vos.add(subAccount.covert());
        }
        return vos;
    }


}
