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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController

public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/login")
    public Result login(@RequestBody UserAuth user) {
        log.info("用户登录：{}", user);
        UserAuth result = userAuthService.login(user);
        User retValue = userService.findUserByUserId(result.getUserId());
        if(retValue != null) {
            if(retValue.getType()==-1){
                return Result.error("黑名单用户无法登录");
            }
            HttpSession session = SessionUtils.getSession();
            if (retValue != null) {
                if (session != null) {
                    session.setAttribute("userId", result.getUserId());
                    log.info("{}",session.getId());
                    log.info("{}",session.getAttribute("userId"));
                }
                return Result.success(retValue);
            }
        }

        return Result.error("User not exist");
    }
}
