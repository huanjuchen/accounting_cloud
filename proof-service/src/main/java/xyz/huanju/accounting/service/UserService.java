package xyz.huanju.accounting.service;


import xyz.huanju.accounting.domain.User;


/**
 * @author HuanJu
 * @date 2020/8/11 20:22
 */
public interface UserService {

    /**
     * 根据 user id获取 user
     *
     * @param userId user id
     * @return user
     */

    User getUser(Integer userId);

    /**
     * 根据token id 获取User
     *
     * @param tokenId token id
     * @return user
     */
    User getUserByToken(String tokenId);

}
