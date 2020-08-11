package xyz.huanju.accounting.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.UserVO;

/**
 * @author HuanJu
 * @date 2020/8/11 2:02
 */
@FeignClient(value = "user-center")
public interface UserServiceFeignClient {


    /**
     * 根据 user id获取 user
     *
     * @param userId user id
     * @return 包含user 的CommonResult
     */
    @GetMapping("/user/{userId}")
    CommonResult<UserVO> getUser(@PathVariable Integer userId);

    /**
     * 根据token id 获取User
     *
     * @return 包含user 的CommonResult
     */
    @GetMapping("/user/by-token")
    CommonResult<UserVO> getUserByToken();
}
