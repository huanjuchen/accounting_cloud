package xyz.huanju.accounting.service.impl;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.huanju.accounting.dao.UserDAO;
import xyz.huanju.accounting.domain.LoginTokenInfo;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.param.LoginParam;
import xyz.huanju.accounting.domain.token.Token;
import xyz.huanju.accounting.exception.BadRequestException;
import xyz.huanju.accounting.service.AuthService;
import xyz.huanju.accounting.service.TokenService;

import javax.annotation.Resource;

/**
 * @author HuanJu
 * @date 2020/8/11 2:19
 */
@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserDAO userDAO;

    @Resource
    private TokenService tokenService;

    @Override
    public LoginTokenInfo userLogin(LoginParam loginParam) {
        log.debug(loginParam.getUsername() + "正在尝试登陆");
        /*
        剥离参数
        并把密码转换成MD5字符串
         */
        String username = loginParam.getUsername();
        String password = SecureUtil.md5(loginParam.getPassword());
        //获取用户
        User user = userDAO.findByName(username);
        if (user == null) {
            throw new BadRequestException(400, "用户名不存在");
        }
        if (!user.getValid()) {
            throw new BadRequestException(400, "用户已被禁用");
        }
        //验证密码
        if (!password.equals(user.getPassword())) {
            throw new BadRequestException(400, "密码不正确");
        }
        Token token = new Token();
        token.setRole(user.getRole()).setUserId(user.getId());
        String tokenId = tokenService.saveToken(token);
        return new LoginTokenInfo(tokenId, user.covert());
    }

    @Override
    public void userLogout(String tokenId) {
        tokenService.removeToken(tokenId);
    }

}
