package xyz.huanju.accounting.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.huanju.accounting.domain.token.Token;
import xyz.huanju.accounting.service.TokenService;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author HuanJu
 * @date 2020/8/9 16:11
 */
@Service
@Slf4j
public class RedisTokenServiceImpl implements TokenService {

    private final static long TWO_HOURS = 2 * 1000 * 60 * 60;

    private final static long ONE_HOURS = 1000 * 60 * 60;

    @Resource(name = "tokenRedisTemplate")
    private RedisTemplate<String, Token> template;

    @Override
    public String saveToken(Token token) {
        log.info("Save in redis, user id: " + token.getUserId());
        token.setTime(System.currentTimeMillis() + TWO_HOURS);
        String key = UUID.randomUUID().toString().replace("-", "");
        template.opsForValue().set(key, token, 2, TimeUnit.HOURS);
        log.info("Save success, user id: " + token.getUserId());
        return key;
    }

    @Override
    public Token getToken(String tokenId) {
        Token token = template.opsForValue().get(tokenId);
        long now = System.currentTimeMillis();
        if (token != null) {

            /*
            检查是否过期
             */
            if (token.getTime() < now) {
                return null;
            }
            /*
            剩余时间小于1小时，更新Key
             */
            if (token.getTime() - (now + ONE_HOURS) < 0) {
                token.setTime(now + TWO_HOURS);
                template.opsForValue().set(tokenId, token, 2, TimeUnit.HOURS);
            }
            return token;
        }
        return null;
    }

    @Override
    public void removeToken(String tokenId) {
        template.delete(tokenId);

    }
}
