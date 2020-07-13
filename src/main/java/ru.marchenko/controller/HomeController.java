package ru.marchenko.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.marchenko.model.entity.User;

/**
 * @author Vladislav Marchenko
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public User getHomePage(@AuthenticationPrincipal User user){
       return user;
    }
}
