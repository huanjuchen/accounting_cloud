package xyz.huanju.accounting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.huanju.accounting.converter.UserConverter;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.UserVO;
import xyz.huanju.accounting.service.PersonalCenterService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author HuanJu
 * @date 2020/8/11 2:08
 */
@RestController
public class PersonalCenterController {

    @Resource
    private PersonalCenterService personalCenterService;


    @GetMapping("/center/info")
    public CommonResult<UserVO> getUserInfo(HttpServletRequest request) {
        String tokenId = getTokenId(request);
        User user = personalCenterService.getUser(tokenId);
        return CommonResult.ok(UserConverter.INSTANCE.convertToVo(user));
    }

    @PutMapping("/center/changeName")
    public CommonResult<UserVO> changeName(HttpServletRequest request, String newName) {
        String tokenId = getTokenId(request);
        personalCenterService.changeName(newName, tokenId);
        return CommonResult.ok();
    }

    @PutMapping("/center/changePhone")
    public CommonResult<UserVO> changePhone(HttpServletRequest request, String newPhone) {
        String tokenId = getTokenId(request);
        personalCenterService.changePhone(newPhone, tokenId);
        return CommonResult.ok();
    }

    @PutMapping("/center/changePwd")
    public CommonResult<UserVO> changePwd(HttpServletRequest request, String newPwd) {
        String tokenId = getTokenId(request);
        personalCenterService.changePassword(newPwd, tokenId);
        return CommonResult.ok();
    }

    private String getTokenId(HttpServletRequest request) {
        return request.getHeader("token_id");
    }


}
