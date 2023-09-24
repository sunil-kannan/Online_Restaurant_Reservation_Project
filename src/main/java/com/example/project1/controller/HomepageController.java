package com.example.project1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import com.example.project1.entities.User;
import com.example.project1.services.UserService;

@Controller
@RequestMapping("/")
public class HomepageController {

    private UserService userService;

    public HomepageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String homepage(Model model) {
        String LoginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
              System.out.println("Not"+authentication);

        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
              model.addAttribute("userRole", "ROLE_ADMIN");
              System.out.println(authentication);
        }

        String anonymousUser = "anonymousUser";
        if (LoginUserName != anonymousUser) {
            User user = userService.findByEmail(LoginUserName);
            model.addAttribute("username", user.getName());
        }
        return "home";
    }
}