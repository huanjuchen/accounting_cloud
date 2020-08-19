package xyz.huanju.accounting.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.UserVO;


/**
 * @author HuanJu
 * @date 2020/8/11 2:02
 */
@FeignClient(value = "${remote.service.user}")
public interface UserServiceFeignClient {


    /**
     * 根据 user id获取 user
     *
     * @param userId user id
     * @return 包含user 的CommonResult
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    CommonResult<UserVO> getUser(@PathVariable(value = "userId") Integer userId);

    /**
     * 根据token id 获取User
     *
     * @param tokenId token id
     * @return 包含user 的CommonResult
     */
    @RequestMapping(value = "/user/token/{tokenId}", method = RequestMethod.GET)
    CommonResult<UserVO> getUserByToken(@PathVariable(value = "tokenId") String tokenId);
}
