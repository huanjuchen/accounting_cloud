package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.token.Token;

/**
 * token 服务
 *
 * @author HuanJu
 * @date 2020/8/9 16:06
 */
public interface TokenService {


    /**
     * 把token存入到容器
     *
     * @param token token
     * @return key/TokenId
     */
    String saveToken(Token token);


    /**
     * 从容器获取Token
     *
     * @param tokenId key
     * @return token
     */
    Token getToken(String tokenId);

    /**
     * 从容器中移除token
     *
     * @param tokenId tokenId
     */
    void removeToken(String tokenId);

}
