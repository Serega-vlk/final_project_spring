package com.example.demo.controllers;

import com.example.demo.dto.Role;
import com.example.demo.entity.Service;
import com.example.demo.entity.User;
import com.example.demo.services.ServicesService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user/admin")
@PreAuthorize("hasAnyAuthority('WRITE')")
public class AdminController {
    private final UserService userService;
    private final ServicesService servicesService;

    @Autowired
    public AdminController(UserService userService, ServicesService servicesService) {
        this.userService = userService;
        this.servicesService = servicesService;
    }

    @GetMapping
    public String adminPage(Model model, Principal principal,
                            @RequestParam(name = "sort", required = false) Optional<String> sort){
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("username", user.getName());
        model.addAttribute("services", servicesService.getAllSorted(sort.orElse("default")));
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("repeatPass", "");
        return "reg";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute() @Valid User user,
                      BindingResult result,
                      @Autowired BCryptPasswordEncoder encoder){
        if (!user.getRepeatPass().equals(user.getPassword())){
            result.addError(new FieldError("user", "repeatPass", "Пароли должны совпадать"));
        }
        if (userService.hasUsername(user.getLogin())){
            result.addError(new FieldError("user", "login", "Такой логин уже существует"));
        }
        if (userService.hasEmail(user.getEmail())){
            result.addError(new FieldError("user", "email", "Такой email уже существует"));
        }
        if (result.hasErrors()){
            return "reg";
        }
        user.setRole(Role.USER);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setMoney(0);
        userService.saveUser(user);
        return "redirect:/user/admin";
    }

    @PostMapping("/block")
    public String blockUser(@RequestParam(name = "block_id") long id){
        userService.blockUserById(id);
        return "redirect:/user/admin";
    }

    @PostMapping("/unblock")
    public String unblockUser(@RequestParam(name = "unblock_id") long id){
        userService.unblockUserById(id);
        return "redirect:/user/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "delete_id") long id){
        userService.deleteById(id);
        return "redirect:/user/admin";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "delete_service_id") long id){
        servicesService.deleteById(id);
        return "redirect:/user/admin";
    }

    @GetMapping("/add")
    public String add(Model model){
        Service service = new Service();
        model.addAttribute("service", service);
        return "add";
    }

    @PostMapping("/add")
    public String newService(@ModelAttribute() @Valid Service service,
                           BindingResult result){
        if (servicesService.hasService(service)){
            result.addError(new FieldError("service", "name", "такая услуга уже существует"));
        }
        if (result.hasErrors()){
            return "add";
        }
        servicesService.saveService(service);
        return "redirect:/user/admin";
    }
}
