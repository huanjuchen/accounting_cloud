package xyz.huanju.accounting.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author HuanJu
 * @date 2020/8/8 20:26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class LoginParam implements Serializable {

    @NotNull(message = "用户名不允许为空")
    private String username;

    @NotNull(message = "密码不允许为空")
    private String password;

}
