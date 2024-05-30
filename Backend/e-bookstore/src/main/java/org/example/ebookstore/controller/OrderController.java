package org.example.ebookstore.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.*;
import org.example.ebookstore.service.BookService;
import org.example.ebookstore.service.CartService;
import org.example.ebookstore.service.OrderService;
import org.example.ebookstore.service.UserService;
import org.example.ebookstore.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController

public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @PostMapping("/getOrder")
    public Result getOrder() {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        return Result.success(orderService.getOrders(userId));
    }

    @PostMapping("/getAllOrder")
    public Result getAllOrder() {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
            return Result.error("无此用户或无权限");
        } else {
            return Result.success(orderService.getAllOrders());
        }
    }

    @PostMapping("/getSelectedOrder")
    public Result getSelectedOrder(@RequestParam("start") String start, @RequestParam("end") String end, @RequestParam("title") String title) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("{},{}", start, end);
        if (!Objects.equals(start, "") && !Objects.equals(end, "")) {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            return Result.success(orderService.getSelectedOrders(userId, startDate, endDate, title));
        } else {
            return Result.success(orderService.getOrdersByTitle(userId, title));
        }
    }

    @PostMapping("/getAllSelectedOrder")
    public Result getAllSelectedOrder(@RequestParam("start") String start, @RequestParam("end") String end, @RequestParam("title") String title) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
            return Result.error("无此用户或无权限");
        } else {
            if (!Objects.equals(start, "") && !Objects.equals(end, "")) {
                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);
                return Result.success(orderService.getAllSelectedOrders(startDate, endDate, title));
            } else {
                return Result.success(orderService.getAllOrdersByTitle(title));
            }
        }
    }

    @PostMapping("/BuybyCartIds")
    public Result BuyByCartIds(@RequestParam("cartIdList") List<Integer> cartIds) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("BuybyCartIds:{}，userId:{}", cartIds, userId);
        orderService.addOrderByCartIds(cartIds, userId);

        return Result.success();
    }

    @PostMapping("/BuybyBookId")
    public Result BuyByBookId(@RequestParam("bookId") Integer bookId, @RequestParam("number") Integer number) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.findUserByUserId(userId);
        if (user == null) {
            return Result.error("未找到用户");
        }
        Book book = bookService.getBookById(bookId);
        if (book == null) {
            return Result.error("未找到书籍");
        }
        orderService.addOrderByBookId(bookId, userId, number);
        return Result.success();
    }

    @PostMapping("/UserStatisticsList")
    public Result UserList(@RequestParam("time") Integer time) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
            return Result.error("无权限");
        }
        if (time < 0 || time > 2) {
            return Result.error("错误的时间范围");
        }
        return Result.success(orderService.getUserList(time));
    }

    @PostMapping("/BookStatisticsList")
    public Result BookList(@RequestParam("time") Integer time) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.findUserByUserId(userId);
        if (user == null || user.getType() != 1) {
            return Result.error("无权限");
        }
        if (time < 0 || time > 2) {
            return Result.error("错误的时间范围");
        }
        return Result.success(orderService.getBookList(time));
    }
}
