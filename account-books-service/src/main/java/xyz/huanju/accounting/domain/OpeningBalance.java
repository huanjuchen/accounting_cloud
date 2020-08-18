package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HuanJu
 * @date 2020/8/16 17:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OpeningBalance implements Serializable {

    private BigDecimal debitBalance;

    private BigDecimal creditBalance;

}
