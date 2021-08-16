package com.example.demo.controllers;

import com.example.demo.dto.UserLoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

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
}
