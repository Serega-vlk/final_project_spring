package com.example.demo.exceptions;

import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.Principal;

@ControllerAdvice
public class AccessDeniedExceptionHandler {
    private final UserRepository repository;

    @Autowired
    public AccessDeniedExceptionHandler(UserRepository repository){
        this.repository = repository;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String denied(AccessDeniedException e,
                         Principal principal){
        if (principal == null)
            return "redirect:/login";
        return "accessDenied";
    }
}
