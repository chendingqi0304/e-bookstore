package org.example.ebookstore.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Result;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;
import org.example.ebookstore.service.UserAuthService;
import org.example.ebookstore.service.UserService;
import org.example.ebookstore.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@Scope("prototype")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/login")
    public Result login(@RequestBody UserAuth user) {
        log.info("用户登录：{}", user);
        Boolean UserExist = userAuthService.login(user);
        if (!UserExist) {
            return Result.error("不存在该用户");
        }
        User retValue = userService.findByAccount(user.getAccount());
        if (retValue != null) {
            if (retValue.getType() == -1) {
                return Result.error("黑名单用户无法登录");
            }
            HttpSession session = SessionUtils.getSession();
            if (retValue != null) {
                if (session != null) {
                    session.setAttribute("userId", retValue.getUserId());
                    log.info("{}", session.getId());
                    log.info("{}", session.getAttribute("userId"));
                }
                return Result.success(retValue);
            }
        }
        return Result.error("User not exist");
    }

    @PostMapping("/logout")
    public Result logout() {
        HttpSession session = SessionUtils.getSession();
        if (session != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            session.invalidate();
            return Result.success(formatDuration(userAuthService.logout(userId)));
        }
        return Result.error("用户未登录");
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long hours = seconds / 3600;
        seconds %= 3600;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
