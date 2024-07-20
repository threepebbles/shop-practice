package com.example.practiceShop.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/home")
    public String home() {
        log.info("home controller");
        return "home";
    }
}