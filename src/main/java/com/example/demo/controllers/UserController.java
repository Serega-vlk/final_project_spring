package com.example.demo.controllers;

import com.example.demo.dto.MoneyDTO;
import com.example.demo.dto.PasswordChangeDTO;
import com.example.demo.entity.Service;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.ServicesService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ServicesService servicesService;

    @Autowired
    public UserController(UserService userService, ServicesService servicesService){
        this.userService = userService;
        this.servicesService = servicesService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ', 'NON')")
    public String userPage(Model model, Principal principal,
                           @RequestParam(name = "sort", required = false) Optional<String> sort){
        if (userService.isBlocked(principal.getName())){
            return "redirect:/blocked";
        }
        User user = userService.getUserByUsername(principal.getName()).orElseThrow(RuntimeException::new);
        if (userService.isUserIsAdmin(user.getLogin())){
            return "redirect:/user/admin";
        }
        model.addAttribute("user", user);
        model.addAttribute("services", servicesService.getAllSorted(sort.orElse("default")));
        return "user";
    }

    @PreAuthorize("hasAnyAuthority('READ', 'NON')")
    @GetMapping("/balance")
    public String balance(Model model, Principal principal){
        User user = userService.getUserByUsername(principal.getName()).orElseThrow(RuntimeException::new);
        model.addAttribute("moneys", new MoneyDTO(0));
        model.addAttribute("userMoney", user.getMoney().toString());
        return "balance";
    }

    @PreAuthorize("hasAnyAuthority('READ', 'NON')")
    @PostMapping("/balance")
    public String addMoney(@ModelAttribute(name = "moneys") @Valid MoneyDTO money,
                           BindingResult result,
                           Model model,
                           Principal principal){
        if (result.hasErrors()){
            User user = userService.getUserByUsername(principal.getName()).orElseThrow(RuntimeException::new);
            model.addAttribute("userMoney", user.getMoney().toString());
            return "balance";
        }
        userService.addMoneyByUsername(principal.getName(), money.getMoneyToAdd());
        userService.checkMoneyAndUnblockUserByUsername(principal.getName(), 60);
        return "redirect:/user";
    }

    @GetMapping("/change")
    @PreAuthorize("hasAnyAuthority('READ')")
    public String changePasswordForm(Model model){
        model.addAttribute("passForm", new PasswordChangeDTO());
        return "changePassword";
    }

    @PostMapping("/change")
    @PreAuthorize("hasAnyAuthority('READ')")
    public String changePassword(@ModelAttribute(name = "passForm") @Valid PasswordChangeDTO passwordChangeDTO,
                                 BindingResult result,
                                 Principal principal,
                                 @Autowired BCryptPasswordEncoder encoder){
        String password = userService.getUserPasswordByUsername(principal.getName());
        if (result.hasErrors()){
            return "changePassword";
        }
        if (encoder.matches(passwordChangeDTO.getOldPassword(), password)){
            userService.appdataPassword(principal.getName(), encoder.encode(passwordChangeDTO.getNewPassword()));
            return "redirect:/user";
        }
        else {
            result.addError(new FieldError("passForm", "oldPassword", "неверный страый пароль"));
            return "changePassword";
        }
    }
}
