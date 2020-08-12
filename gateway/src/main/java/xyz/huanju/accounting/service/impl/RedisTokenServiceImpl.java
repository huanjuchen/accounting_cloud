package xyz.huanju.accounting.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.huanju.accounting.domain.token.Token;
import xyz.huanju.accounting.service.TokenService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author HuanJu
 * @date 2020/8/13 0:08
 */
@Component
@Slf4j
public class RedisTokenServiceImpl implements TokenService {

    private RedisTemplate<String, Token> redisTemplate;

    @Autowired
    @Qualifier("tokenTemplate")
    public void setRedisTemplate(RedisTemplate<String, Token> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final static long TWO_HOURS = 2 * 1000 * 60 * 60;

    private final static long ONE_HOURS = 1000 * 60 * 60;

    @Override
    public Token getToken(String tokenId) {
        long now = System.currentTimeMillis();
        Token token = redisTemplate.opsForValue().get(tokenId);
        if (token == null) {
            return null;
        }
        long expirationTime = token.getTime();
        /*
         是否过期
         */
        if (expirationTime - now <= 0) {
            return null;
        }

        /*
         时间不足一个小时
         更新key
         */
        if (expirationTime - now < ONE_HOURS) {
            token.setTime(now + TWO_HOURS);
            redisTemplate.opsForValue().set(tokenId, token, 2, TimeUnit.HOURS);
        }
        return token;
    }


}
