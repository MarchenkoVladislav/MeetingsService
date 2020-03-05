package ru.marchenko.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.marchenko.model.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladislav Marchenko
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(Model model , @AuthenticationPrincipal User user){
        Map<String, Object> data = new HashMap<>();

        data.put("user", user);

        model.addAttribute("signInData", data);

        return "homePage";
    }
}
