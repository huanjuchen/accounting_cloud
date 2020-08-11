package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.UserVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 *
 * @author HuanJu
 * @date 2020/8/8 17:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    private Integer id;

    @NotNull(message = "用户名不能为空")
    private String username;

    private String password;
    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    private String name;
    /**
     * 1：超级管理员
     * 2：主管会计
     * 3：普通会计
     */
    private Integer role;

    private String phone;

    private Timestamp joinTime;

    private Boolean valid;


    public UserVO covert() {
        UserVO userVo = new UserVO();
        userVo.setId(this.id).setUsername(this.username)
                .setName(this.name)
                .setPhone(this.phone)
                .setRole(this.role)
                .setValid(this.valid)
                .setJoinTime(this.joinTime);
        return userVo;
    }

    public static List<UserVO> listCovert(List<User> users){
        List<UserVO> userVos = new ArrayList<>(users.size());
        for (User user : users) {
            userVos.add(user.covert());
        }
        return userVos;
    }


}
