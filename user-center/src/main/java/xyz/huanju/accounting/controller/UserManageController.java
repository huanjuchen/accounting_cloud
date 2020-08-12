package xyz.huanju.accounting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.huanju.accounting.converter.UserConverter;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.UserVO;
import xyz.huanju.accounting.service.UserManageService;

import javax.annotation.Resource;


/**
 * @author HuanJu
 * @date 2020/8/9 22:18
 */
@RestController
@Slf4j
public class UserManageController {

    @Resource
    private UserManageService userManageService;


    @PostMapping("/admin/user")
    public CommonResult<UserVO> createUser(@RequestBody @Validated User user) {
        User temp = userManageService.save(user);
        return CommonResult.ok(UserConverter.INSTANCE.convertToVo(user));
    }

    @PutMapping("/admin/user/resetPwd/{userId}")
    public CommonResult<UserVO> resetPassword(@PathVariable(value = "userId") Integer userId) {
        userManageService.resetPwd(userId);
        return CommonResult.ok();
    }

    @PutMapping("/admin/user/lock/{userId}")
    public CommonResult<UserVO> lockUser(@PathVariable(value = "userId") Integer userId) {
        userManageService.lockUser(userId);
        return CommonResult.ok();
    }

    @PutMapping("/admin/user/unlock/{userId}")
    public CommonResult<UserVO> unLockUser(@PathVariable(value = "userId") Integer userId) {
        userManageService.unLockUser(userId);
        return CommonResult.ok();
    }



}
