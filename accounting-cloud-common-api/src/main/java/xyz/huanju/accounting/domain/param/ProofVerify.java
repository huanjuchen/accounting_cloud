package xyz.huanju.accounting.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author HuanJu
 * @date 2020/8/8 20:29
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ProofVerify {

    @NotNull(message = "凭证号不允许为空")
    private Integer id;

    @NotNull(message = "审核结果不能为空")
    private Boolean result;

}
