package xyz.huanju.accounting.service.impl;

import org.springframework.stereotype.Service;
import xyz.huanju.accounting.dao.UserDAO;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.token.Token;
import xyz.huanju.accounting.service.TokenService;
import xyz.huanju.accounting.service.UserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 2:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Resource
    private TokenService tokenService;

    @Override
    public User find(Integer id) {
        return userDAO.find(id);
    }

    @Override
    public User getUserByToken(String tokenId) {
        Token token = tokenService.getToken(tokenId);
        if (token==null){
            return null;
        }else {
            return userDAO.find(token.getUserId());
        }
    }

    @Override
    public List<User> getUserList(Map<String, Object> map) {
        return userDAO.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userDAO.count(map);
    }

}
