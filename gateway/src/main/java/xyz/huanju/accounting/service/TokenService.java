package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.token.Token;

/**
 * @author HuanJu
 * @date 2020/8/13 0:07
 */
public interface TokenService {

    /**
     * 从容器获取token
     *
     * @param tokenId token id
     * @return token
     */
    Token getToken(String tokenId);

}
