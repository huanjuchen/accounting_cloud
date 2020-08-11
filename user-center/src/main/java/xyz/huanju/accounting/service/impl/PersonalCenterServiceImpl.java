package xyz.huanju.accounting.service.impl;

import cn.hutool.crypto.SecureUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.huanju.accounting.dao.UserDAO;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.token.Token;
import xyz.huanju.accounting.exception.AccountingException;
import xyz.huanju.accounting.exception.BadRequestException;
import xyz.huanju.accounting.exception.BadUpdateException;
import xyz.huanju.accounting.service.PersonalCenterService;
import xyz.huanju.accounting.service.TokenService;

import javax.annotation.Resource;

/**
 * @author HuanJu
 * @date 2020/8/11 2:11
 */
@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class PersonalCenterServiceImpl implements PersonalCenterService {


    @Resource
    private UserDAO userDAO;

    @Resource
    private TokenService tokenService;

    @Override
    public User getUser(String tokenId) {
        Integer userId=getUserId(tokenId);
        return userDAO.find(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void changeName(String newName, String tokenId) {

        Integer userId = getUserId(tokenId);
        if (userId == null) {
            throw new BadUpdateException(400, "无法修改");
        }
        User user = new User();
        user.setId(userId).setName(newName);
        int rows = userDAO.update(user);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void changePhone(String newPhone, String tokenId) {

        Integer userId = getUserId(tokenId);
        if (userId == null) {
            throw new BadUpdateException(400, "无法修改");
        }
        User user = new User();
        user.setId(userId).setPhone(newPhone);
        int rows = userDAO.update(user);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void changePassword(String newPwd, String tokenId) {

        Integer userId = getUserId(tokenId);
        if (userId == null) {
            throw new BadUpdateException(400, "无法修改");
        }
        User user = new User();
        user.setId(userId).setPassword(SecureUtil.md5(newPwd));
        int rows = userDAO.update(user);
        if (rows != 1) {
            throw new AccountingException(500, "系统错误");
        }
        tokenService.removeToken(tokenId);
    }

    private Integer getUserId(String tokenId) {
        Token token = tokenService.getToken(tokenId);
        if (token == null) {
            throw new BadRequestException(400, "请求错误");
        }
        return token.getUserId();
    }
}
