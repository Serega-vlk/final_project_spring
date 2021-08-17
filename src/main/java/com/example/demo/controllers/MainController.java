package com.example.demo.controllers;

import com.example.demo.dto.UserLoginInfo;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {
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
    public String blocked(){
        return "blocked";
    }
}
