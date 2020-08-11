package xyz.huanju.accounting.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.UserVO;


/**
 * @author HuanJu
 * @date 2020/8/9 22:49
 */
@RestController
public class TestController {

    @PostMapping("/test/user")
    public CommonResult<Object> validTest(@Validated @RequestBody User user){
        return CommonResult.ok(user);
    }


}
