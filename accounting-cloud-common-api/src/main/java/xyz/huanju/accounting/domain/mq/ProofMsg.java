package xyz.huanju.accounting.domain.mq;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HuanJu
 * @date 2020/8/13 19:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProofMsg implements Serializable {

    /**
     * verified user id
     */
    private Integer verifiedUserId;

    /**
     * proof id
     */
    private Integer proofId;

    /**
     * date
     */
    private Date date;

}
