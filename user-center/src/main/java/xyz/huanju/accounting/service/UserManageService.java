package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.LoginTokenInfo;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.param.LoginParam;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/9 1:44
 */
public interface UserManageService {

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 用户信息
     */
    User save(User user);


    /**
     * 重置用户密码
     *
     * @param userId 用户ID
     */
    void resetPwd(Integer userId);


    /**
     * 禁用用户
     *
     * @param userId 用户ID
     */
    void lockUser(Integer userId);

    /**
     * 启用用户
     *
     * @param userId 用户ID
     */
    void unLockUser(Integer userId);


}
