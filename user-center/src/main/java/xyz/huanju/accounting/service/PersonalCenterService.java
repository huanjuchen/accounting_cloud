package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.User;

/**
 * @author HuanJu
 * @date 2020/8/11 2:10
 */
public interface PersonalCenterService {

    /**
     * 根据 tokenId 获取用户
     * @param tokenId tokenId
     * @return user
     */
    User getUser(String tokenId);

    /**
     * 更改姓名
     * @param newName 新姓名
     * @param tokenId tokenId
     */
    void changeName(String newName, String tokenId);

    /**
     * 更改手机号
     * @param newPhone 新手机号
     * @param tokenId tokenId
     */
    void changePhone(String newPhone, String tokenId);

    /**
     *
     * 修改密码
     *
     * @param newPwd 新密码
     * @param tokenId token Id
     */
    void changePassword(String newPwd, String tokenId);

}
