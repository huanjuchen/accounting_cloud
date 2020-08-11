package xyz.huanju.accounting.domain.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author HuanJu
 * @date 2020/8/9 16:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Token {

    private String tokenId;

    private Integer userId;

    private Integer role;

    /**
     * 过期时间
     */
    private long time;
}
