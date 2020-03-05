package ru.marchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.marchenko.model.entity.User;
import ru.marchenko.service.UsersService;

import javax.servlet.http.HttpSession;

/**
 * @author Vladislav Marchenko
 */
@RestController
@RequestMapping("/users/")
@SessionAttributes("userID")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "")
    public User getUserByEmail(@RequestParam String email, HttpSession session) {
        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            return usersService.getUserbyEmail(email);
        }

        else {
            return null;
        }
    }
}
