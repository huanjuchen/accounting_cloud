package xyz.huanju.accounting.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuanJu
 * @date 2020/8/8 18:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProofItemVO implements Serializable {

    private Integer id;

    private String abstraction;

    private SubjectVO debitSubSubject;

    private SubjectVO creditSubSubject;

    private SubjectVO debitLedgerSubject;

    private SubjectVO creditLedgerSubject;

    private BigDecimal money;

    private Boolean charge;

}
