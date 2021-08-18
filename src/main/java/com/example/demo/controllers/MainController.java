package com.example.demo.controllers;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService service) {
        this.userService = service;
    }

    @GetMapping
    public String main(){
        return "main";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String logConfirm(){
        return "redirect:/user";
    }

    @PreAuthorize("hasAnyAuthority('NON')")
    @GetMapping("/blocked")
    public String blocked(Principal principal,
                          Model model){
        model.addAttribute("user", userService.getUserByUsername(principal.getName())
                .orElseThrow(RuntimeException::new));
        return "blocked";
    }
}
