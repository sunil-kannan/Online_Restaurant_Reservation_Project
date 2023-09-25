package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.project1.dto.UserDto;
import com.example.project1.entities.User;
import com.example.project1.services.*;

import jakarta.validation.Valid;



@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;        
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "signup";
    }

    // method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())){
            result.rejectValue("confirmPassword", null, "Password doesn't match with Confirm Password");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "signup";
        }
        userService.saveUser(user);
        return "redirect:/login?registeredSuccessfully";
    }

}