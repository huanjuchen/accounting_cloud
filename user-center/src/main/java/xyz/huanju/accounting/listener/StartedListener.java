package xyz.huanju.accounting.listener;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.UserDAO;
import xyz.huanju.accounting.domain.User;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author HuanJu
 * @date 2020/8/9 19:08
 */
@Component
@Slf4j
public class StartedListener implements ApplicationListener<ApplicationReadyEvent> {

    private UserDAO userDAO;

    @Resource
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        User rootUser=userDAO.findByName("root");
        if (rootUser==null){
            User user=new User();
            user.setUsername("root");
            user.setPassword(SecureUtil.md5("12345678"));
            user.setName("超级管理员");
            user.setRole(1);
            user.setJoinTime(new Timestamp(System.currentTimeMillis()));
            user.setValid(true);

            userDAO.save(user);
            log.info("系统初次使用，建立超级管理员用户---");
            log.info("用户名：root   密码：12345678");
        }else {
            log.info("root 用户已存在");
        }
    }
}
