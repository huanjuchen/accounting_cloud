package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 2:23
 */
public interface UserService {


    /**
     * 查找指定用户
     *
     * @param id 用户ID
     * @return 指定用户
     */
    User find(Integer id);


    /**
     * 根据token id获取用户
     *
     * @param tokenId token id
     * @return user
     */
    User getUserByToken(String tokenId);


    /**
     * 根据条件获取用户
     *
     * @param map 条件
     * @return 用户列表
     */
    List<User> getUserList(Map<String, Object> map);

    /**
     * 获取满足条件的用户数量
     *
     * @param map 条件
     * @return 满足条件的用户数量
     */
    int count(Map<String, Object> map);

}
