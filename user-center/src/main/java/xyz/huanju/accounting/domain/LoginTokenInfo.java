package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.UserVO;

/**
 * @author HuanJu
 * @date 2020/8/9 2:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginTokenInfo {

    private String tokenId;

    private UserVO user;

}
