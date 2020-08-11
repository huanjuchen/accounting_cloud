package xyz.huanju.accounting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.huanju.accounting.converter.UserConverter;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.UserVO;
import xyz.huanju.accounting.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 2:32
 */
@RestController
@Slf4j
public class UserController {

    private UserConverter userConverter=UserConverter.INSTANCE;

    @Resource
    private UserService userService;

    @GetMapping({"/admin/user", "/user"})
    public CommonResult<List<UserVO>> getUserList(@RequestParam(required = false, name = "page") String page,
                                    @RequestParam(required = false, name = "pageSize") String pageSize,
                                    @RequestParam(required = false, name = "selectWord") String selectWord,
                                    @RequestParam(required = false, name = "desc") String desc,
                                    @RequestParam(required = false, name = "valid") String valid) {
        log.debug("page=" + page + "  pageSize=" + pageSize + "  selectWord=" + selectWord + "  valid=" + valid);
        Map<String, Object> map = new HashMap<>();
        Integer pageInt = null;
        Integer pageSizeInt = null;

        if (selectWord != null && !"".equals(selectWord)) {
            try {
                Integer userId = Integer.valueOf(selectWord);
                User user = userService.find(userId);
                if (user != null) {
                    List<UserVO> userVos = new ArrayList<>();
                    userVos.add(user.covert());
                    return CommonResult.ok(userVos);
                }
            } catch (NumberFormatException e) {
                map.put("nameSw", selectWord);
            }
        }

        if (valid != null && !"".equals(valid)) {
            map.put("valid", Boolean.valueOf(valid));
        }

        if (desc != null && desc.length() > 0) {
            map.put("desc", "desc");
        }

        try {
            if (page != null && !"".equals(page)) {
                pageInt = Integer.valueOf(page);
            }
        } catch (NumberFormatException e) {
            log.debug("page的值无法正常转换，已忽略");
        }

        try {
            if (pageSize != null && !"".equals(pageSize)) {
                pageSizeInt = Integer.valueOf(pageSize);
            }
        } catch (NumberFormatException e) {
            log.debug("pageSize的值无法正常转换，已忽略");
        }


        if (pageInt != null && pageSizeInt != null) {
            if (pageInt < 1) {
                pageInt = 1;
            }
            if (pageSizeInt < 1) {
                pageSizeInt = 1;
            }
            map.put("offset", (pageInt - 1) * pageSizeInt);
            map.put("count", pageSizeInt);
        } else {
            map.put("offset", 0);
            map.put("count", 10);
        }
        List<User> userList = userService.getUserList(map);
        return CommonResult.ok(userConverter.convertToVoList(userList));
    }

    @GetMapping({"/admin/user/count", "/user/count"})
    public CommonResult<Integer> count(
            @RequestParam(required = false, name = "selectWord") String selectWord,
            @RequestParam(required = false, name = "valid") String valid) {
        Map<String, Object> map = new HashMap<>(4, 0.75f);

        if (selectWord != null && !"".equals(selectWord)) {
            try {
                Integer userId = Integer.valueOf(selectWord);
                User user = userService.find(userId);
                if (user != null) {
                    return CommonResult.ok(1);
                }

            } catch (NumberFormatException e) {
                map.put("nameSw", selectWord);
            }
        }

        if (valid != null && !"".equals(valid)) {
            map.put("valid", Boolean.valueOf(valid));
        }
        int count = userService.count(map);
        return CommonResult.ok(count);
    }


    @GetMapping({"/admin/user/{userId}", "/user/{userId}"})
    public CommonResult<UserVO> getUser(@PathVariable Integer userId) {
        User user = userService.find(userId);
        return CommonResult.ok(userConverter.convertToVo(user));
    }

    @GetMapping({"/admin/user/by-token", "/user/by-token"})
    public CommonResult<UserVO> getUserByToken(HttpServletRequest request) {
        String tokenId = request.getHeader("token_id");
        User user = userService.getUserByToken(tokenId);
        return CommonResult.ok(userConverter.convertToVo(user));
    }


}
