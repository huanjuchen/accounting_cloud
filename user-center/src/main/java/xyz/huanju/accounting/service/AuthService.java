package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.LoginTokenInfo;
import xyz.huanju.accounting.domain.param.LoginParam;

/**
 * @author HuanJu
 * @date 2020/8/11 2:13
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginParam 登录参数
     * @return tokenId以及用户信息
     */
    LoginTokenInfo userLogin(LoginParam loginParam);

    /**
     * 用户退出
     *
     * @param tokenId tokenId
     */
    void userLogout(String tokenId);
}
