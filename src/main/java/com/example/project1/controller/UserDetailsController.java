package com.example.project1.controller;


import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.project1.entities.*;
import com.example.project1.services.UserService;
import java.util.*;

@Controller
@PreAuthorize("hasRole('MANAGER')")
@RequestMapping("/users")
public class UserDetailsController {

    private UserService userServices;

    public UserDetailsController(UserService userServices) {
        this.userServices = userServices;
    }

    // method to show all user details
    @GetMapping("")
    public String listRegisteredUsers(Authentication authentication, Model model) {
        String LoginUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        int pageNo = 0;
        int pageSize = 5;
        String sortField = "name";
        String sortDir = "asc";
        String keyword = "";
        Page<User> page = userServices.findPaginated(pageNo, pageSize, sortField, sortDir, keyword);
        List<User> users = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("key", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("users", users);
        model.addAttribute("username", LoginUserName);
        model.addAttribute("users", users);
        return "users";
    }

    // method will show the form to update roles of the user
    @GetMapping("/showFormForUpdateUser/{id}")
    public String editUserRole(@PathVariable Long id, Model model) {
        User users = userServices.getUserById(id);
        List<Role> allRoles = userServices.getAllRole();
        if (users != null) {
            model.addAttribute("users", users);
            model.addAttribute("roles", allRoles);
            return "updateUsers";
        } else {
            model.addAttribute("userNotFound", id);
            return "redirect:/users/?userNotFound";
        }
    }

    // post method for updating roles
    @PostMapping("/UserUpdate/{id}")
    public String updateUser(@PathVariable(value = "id") Long id, Model model, User user) {
        User opt = userServices.getUserById(id);

        if (opt != null) {
            opt.setRoles(user.getRoles());
            userServices.saveUserforRole(opt);
            return "redirect:/users";
        } else {
            model.addAttribute("userNotFound", id);
            return "redirect:/users?userNotFound";
        }
    }

    // this method is to filtering the users
    @GetMapping("/page/{pageNo}")
    public String findPaginated(Authentication authentication, @PathVariable(value = "pageNo") int pageNo,
            @RequestParam("keyword") String keyword,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {
        int pageSize = 5;
        Page<com.example.project1.entities.User> page = userServices.findPaginated(pageNo, pageSize, sortField, sortDir, keyword);
        List<User> users = page.getContent();

        String LoginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", LoginUserName);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("key", keyword);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("users", users);
        return "users";
    }
}

