package org.example.ebookstore.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.ebookstore.entity.Book;
import org.example.ebookstore.entity.Cart;
import org.example.ebookstore.entity.Result;
import org.example.ebookstore.entity.User;
import org.example.ebookstore.service.BookService;
import org.example.ebookstore.service.CartService;
import org.example.ebookstore.service.UserService;
import org.example.ebookstore.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController

public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @PostMapping("/getCart")
    public Result getCart(@RequestParam("index")int pageIndex, @RequestParam("size")int pageSize) {
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        log.info("userId: {}", userId);
        Pageable cartpage= PageRequest.of(pageIndex, pageSize);
        Page<Cart> carts=cartService.getCarts(userId,cartpage);
        return Result.success(carts);
    }
    @PostMapping("/updateCartNum")
    public Result updateCartNumber(@RequestParam("cartId") Integer cartId, @RequestParam("number") Integer number){
        cartService.updateCartNumber(cartId,number);
        return Result.success();
    }
    @PostMapping("/addCartbyBookId")
    public Result addCart( @RequestParam("bookId") Integer bookId, @RequestParam("number")Integer number){
        HttpSession session = SessionUtils.getSession();
        log.info("sessionID: {}", session.getId());
        Integer userId = (Integer) session.getAttribute("userId");
        User user= userService.findUserByUserId(userId);
        if (user==null){
            return Result.error("未找到用户");
        }
        Book book=bookService.getBookById(bookId);
        if (book==null){
            return Result.error("未找到书籍");
        }
        Cart cart=new Cart();
        cart.setBook(bookService.getBookById(bookId));
        cart.setUserId(userId);
        cart.setNumber(number);
        if(cartService.addCart(cart)){
            return Result.success();
        }else {
            return Result.error("existed");
        }
    }
}
