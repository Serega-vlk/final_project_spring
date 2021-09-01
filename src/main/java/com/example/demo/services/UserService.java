package com.example.demo.services;

import com.example.demo.dto.Role;
import com.example.demo.entity.Service;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class UserService {
    private final UserRepository uRepo;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository uRepo, BCryptPasswordEncoder encoder){
        this.uRepo = uRepo;
        this.encoder = encoder;
    }

    public User getUserByUsername(String username){
        return uRepo.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким логином не найден"));
    }

    @Transactional
    public User appdataPassword(String username, String newPassword){
        User user = getUserByUsername(username);
        user.setPassword(newPassword);
        return uRepo.save(user);
    }

    public boolean isUserIsAdmin(String username){
        return getUserByUsername(username).getRole().equals(Role.ADMIN);
    }

    public List<User> getAllUsers(){
        return uRepo.findAll();
    }

    public String getUserPasswordByUsername(String username){
        return getUserByUsername(username).getPassword();
    }

    public boolean isBlocked(User user){
        return user.getRole().equals(Role.BLOCKED);
    }

    public User saveUser(User user){
        user.setRole(Role.USER);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setMoney(0);
        return uRepo.save(user);
    }

    @Transactional
    public User blockUserById(Long id){
        User user = uRepo.getById(id);
        user.setRole(Role.BLOCKED);
        return uRepo.save(user);
    }

    @Transactional
    public User unblockUserById(long id){
        User user = uRepo.getById(id);
        user.setRole(Role.USER);
        return uRepo.save(user);
    }

    public void deleteById(long id){
        uRepo.deleteById(id);
    }

    public boolean hasUsername(String username){
        return uRepo.findByLogin(username).isPresent();
    }

    public boolean hasEmail(String email){
        return uRepo.findByEmail(email).isPresent();
    }

    @Transactional
    public User addMoneyByUsername(String username, int money){
        User user = getUserByUsername(username);
        int userMoney = user.getMoney();
        user.setMoney(userMoney + money);
        return uRepo.save(user);
    }

    public User unblockUserByUsername(String username){
        User user = getUserByUsername(username);
        user.setRole(Role.USER);
        return uRepo.save(user);
    }

    @Transactional
    public User addServiceByUsername(String username, Service service){
        User user = getUserByUsername(username);
        payForService(user, service);
        user.addService(service);
        return uRepo.save(user);
    }

    public int payForService(User user, Service service){
        user.setMoney(user.getMoney() - service.getPrice());
        return user.getMoney();
    }

    @Transactional
    public User deleteServiceByUsername(String username, Service service){
        User user = getUserByUsername(username);
        user.deleteService(service);
        return uRepo.save(user);
    }

    public boolean checkEnoughMoney(User user, Service service){
        return user.getMoney() >= service.getPrice();
    }
}
