package org.example.nginxdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@Slf4j
public class Controller {
    @GetMapping("/")
    public String Greeting() {
        log.info("Server One");
        return "Server One";
    }
}
