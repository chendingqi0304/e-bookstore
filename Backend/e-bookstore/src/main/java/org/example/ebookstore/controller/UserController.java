package org.example.ebookstore.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Result;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.entity.UserAuth;
import org.example.ebookstore.repository.UserAuthRepository;
import org.example.ebookstore.service.BookService;
import org.example.ebookstore.service.UserAuthService;
import org.example.ebookstore.service.UserService;
import org.example.ebookstore.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/newUser")
    public Result save(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) {

        User user1 = userService.findByemail(email);
        User user2 = userService.findByUsername(username);
        if (user1 != null || user2 != null) {
            return Result.error("user existed");
        } else {
            User user = new User();
            UserAuth userAuth = new UserAuth();
            user.setUsername(username);
            user.setEmail(email);
            user.setType(0);
            User newuser = userService.save(user);
            userAuth.setUserId(newuser.getUserId());
            userAuth.setAccount(newuser.getAccount());
            userAuth.setPassword(password);
            userAuthRepository.save(userAuth);
            return Result.success(newuser.getAccount());
        }
    }

    @PostMapping("/getStatistics")
    public Result getStatistics() {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        return Result.success(userService.getStatistics(userId));
    }


    @PostMapping("/findUserByemail")
    public Result findByemail(@RequestBody String email) {
        User user = userService.findByemail(email);
        if (user != null) {
            return Result.success(user.getAccount());
        } else {
            return Result.error("not found");
        }
    }

    @PostMapping("/findUserByuserId")
    public Result findByUserId(@RequestParam("userId") Integer userId) {
        User user = userService.findUserByUserId(userId);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("未找到用户");
        }
    }

    @PostMapping("/editType")
    public Result editType(@RequestParam("userId") Integer userId, @RequestParam("type") Integer type) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer MyId = (Integer) session.getAttribute("userId");
        if (type > 1 || type < -1) {
            return Result.error("错误的权限");
        }
        User user = userService.findUserByUserId(MyId);
        if (user != null) {
            if (user.getType() != 1) {
                return Result.error("您不是管理员用户，无权修改");
            } else {
                User target = userService.findUserByUserId(userId);
                if (target != null) {
                    if (MyId == userId) {
                        return Result.error("请不要修改自己的权限");
                    } else {
                        target.setType(type);
                        userService.save(target);
                        return Result.success();
                    }
                } else {
                    return Result.error(" 未找到目标用户");
                }
            }
        } else {
            return Result.error("未找到您的账号");
        }
    }
}