package xyz.huanju.accounting.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.huanju.accounting.domain.LoginTokenInfo;
import xyz.huanju.accounting.domain.param.LoginParam;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.service.AuthService;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 2:06
 */
@RestController
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public CommonResult<LoginTokenInfo> login(@Validated @RequestBody LoginParam loginParam) {
        LoginTokenInfo loginTokenInfo = authService.userLogin(loginParam);
        return CommonResult.ok(loginTokenInfo);
    }


    @GetMapping("/logout")
    public CommonResult<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        String tokenId = request.getHeader("token_id");
        if (tokenId != null) {
            authService.userLogout(tokenId);
        }
        return CommonResult.ok();
    }


}
