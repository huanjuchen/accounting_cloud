package xyz.huanju.accounting.service.impl;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.huanju.accounting.dao.UserDAO;
import xyz.huanju.accounting.exception.*;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.service.TokenService;
import xyz.huanju.accounting.service.UserManageService;
import xyz.huanju.accounting.utils.JsonUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
/**
 * @author HuanJu
 * @date 2020/8/9 13:21
 */
@Service
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
@Slf4j
public class UserManageServiceImpl implements UserManageService {

    @Resource
    private UserDAO userDAO;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public User save(User user) {
        log.debug(JsonUtils.toJson(user));
        if (user.getRole() == 1) {
            throw new BadCreateException(400, "不允许添加用户为超级管理员");
        }

        user.setValid(true);
        user.setJoinTime(new Timestamp(System.currentTimeMillis()));
        user.setPassword(SecureUtil.md5("12345678"));

        if (userDAO.findByName(user.getUsername()) != null) {
            throw new AlreadyExistsException(400, "用户已存在");
        }
        userDAO.save(user);
        User temp = userDAO.find(user.getId());
        log.info(user.getUsername() + "用户添加成功!");
        return temp;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void resetPwd(Integer userId) {
        User user = userDAO.find(userId);
        if (user == null) {
            throw new NotFoundException(400, "未找到指定用户");
        }
        user = new User();
        user.setId(userId);
        user.setPassword(SecureUtil.md5("12345678"));
        int rows = userDAO.update(user);

        if (rows != 1) {
            throw new BadUpdateException(500, "修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void lockUser(Integer userId) {
        log.debug("正在禁用编号为 " + userId + " 用户");
        User user = userDAO.find(userId);
        if (user == null) {
            throw new BadRequestException(400, "非法请求");
        }
        if ("root".equals(user.getUsername())) {
            throw new BadRequestException(400, "非法请求，该用户无法被禁用");
        }
        user = new User();
        user.setId(userId);
        user.setValid(false);
        int rows = userDAO.update(user);
        if (rows != 1) {
            throw new BadUpdateException(500, "修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void unLockUser(Integer userId) {
        log.debug("正在启用编号为 " + userId + " 的用户");
        User user = userDAO.find(userId);
        if (user == null) {
            throw new BadRequestException(400, "非法请求");
        }
        if ("root".equals(user.getUsername())) {
            throw new BadRequestException(400, "无效的请求");
        }

        user = new User();
        user.setId(userId);
        user.setValid(true);
        int rows = userDAO.update(user);

        if (rows != 1) {
            throw new BadUpdateException(500, "修改失败");
        }
    }






}
