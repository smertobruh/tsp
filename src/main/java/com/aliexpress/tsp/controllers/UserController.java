package com.aliexpress.tsp.controllers;

import com.aliexpress.tsp.model.User;
import com.aliexpress.tsp.services.CustomUserDetailsService;
import com.aliexpress.tsp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final ConversionService conversionService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(User user){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        System.out.println(user);
        if(!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Такой пользователь уже существует");
        }
        userService.createUser(user);
        return "redirect:/login";
    }
}
