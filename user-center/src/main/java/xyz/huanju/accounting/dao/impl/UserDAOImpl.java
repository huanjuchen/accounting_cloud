package xyz.huanju.accounting.dao.impl;

import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.UserDAO;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/9 16:42
 */
@Component
public class UserDAOImpl implements UserDAO {

    @Resource
    private UserMapper userMapper;

    @Override
    public int save(User obj) {
        return userMapper.save(obj);
    }

    @Override
    public int update(User obj) {
        return userMapper.update(obj);
    }

    @Override
    public int delete(Integer key) {
        return userMapper.delete(key);
    }

    @Override
    public User find(Integer key) {
        return userMapper.find(key);
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public List<User> list(Map<String, Object> map) {
        return userMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }
}
