package xyz.huanju.accounting.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.huanju.accounting.converter.UserConverter;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.response.ResultCode;
import xyz.huanju.accounting.domain.vo.UserVO;
import xyz.huanju.accounting.exception.RemoteCallException;
import xyz.huanju.accounting.feign.UserServiceFeignClient;
import xyz.huanju.accounting.service.UserService;

import javax.annotation.Resource;

/**
 * @author HuanJu
 * @date 2020/8/11 20:31
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserConverter converter=UserConverter.INSTANCE;

    @Resource
    private UserServiceFeignClient userServiceFeignClient;

    @Override
    public User getUser(Integer userId) {
        return userResultHandle(userServiceFeignClient.getUser(userId));
    }

    @Override
    public User getUserByToken(String tokenId) {
        return userResultHandle(userServiceFeignClient.getUserByToken(tokenId));
    }

    private User userResultHandle(CommonResult<UserVO> commonResult){
        if (commonResult==null){
            return null;
        }
        if (commonResult.getCode()!=200){
            log.error("远程服务调用错误");
            throw new RemoteCallException(ResultCode.INTERNAL_SERVER_ERROR,"服务器内部错误");
        }
        UserVO userVO=commonResult.getData();
        if (userVO==null){
            return null;
        }
        return converter.convertToPojo(userVO);
    }

}
