package com.example.demo.services;

import com.example.demo.dto.Role;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository uRepo;

    @Autowired
    public UserService(UserRepository uRepo){
        this.uRepo = uRepo;
    }

    public Optional<User> getUserByUsername(String username){
        return uRepo.findByLogin(username);
    }

    @Transactional
    public User appdataPassword(String username, String newPassword){
        User user = uRepo.findByLogin(username).orElseThrow(RuntimeException::new);
        user.setPassword(newPassword);
        return uRepo.save(user);
    }

    public boolean isUserIsAdmin(String username){
        return uRepo.findByLogin(username).orElseThrow(RuntimeException::new).getRole().equals(Role.ADMIN);
    }

    public List<User> getAllUsers(){
        return uRepo.findAll();
    }

    public String getUserPasswordByUsername(String username){
        User user = getUserByUsername(username).orElseThrow(RuntimeException::new);
        return user.getPassword();
    }

    public boolean isBlocked(User user){
        return user.getRole().equals(Role.BLOCKED);
    }

    public User saveUser(User user){
        return uRepo.save(user);
    }

    @Transactional
    public User blockUserById(Long id){
        User user = uRepo.getById(id);
        user.setRole(Role.BLOCKED);
        return uRepo.save(user);
    }

    @Transactional
    public User unblockUserById(int id){
        User user = uRepo.getById((long) id);
        user.setRole(Role.USER);
        return uRepo.save(user);
    }

    public void deleteById(int id){
        uRepo.deleteById((long) id);
    }

    public boolean hasUsername(String username){
        return uRepo.findByLogin(username).isPresent();
    }

    public boolean hasEmail(String email){
        return uRepo.findByEmail(email).isPresent();
    }

    @Transactional
    public User addMoneyByUsername(String username, int money){
        User user = getUserByUsername(username).orElseThrow(RuntimeException::new);
        int userMoney = user.getMoney();
        user.setMoney(userMoney + money);
        return uRepo.save(user);
    }

    public User unblockUserByUsername(String username){
        User user = getUserByUsername(username).orElseThrow(RuntimeException::new);
        user.setRole(Role.USER);
        return uRepo.save(user);
    }

    @Transactional
    public User addServiceByUsername(String username, com.example.demo.entity.Service service){
        User user = uRepo.findByLogin(username).orElseThrow(RuntimeException::new);
        payForService(user, service);
        user.addService(service);
        return uRepo.save(user);
    }

    public int payForService(User user, com.example.demo.entity.Service service){
        user.setMoney(user.getMoney() - service.getPrice());
        return user.getMoney();
    }

    @Transactional
    public User deleteServiceByUsername(String username, com.example.demo.entity.Service service){
        User user = uRepo.findByLogin(username).orElseThrow(RuntimeException::new);
        user.deleteService(service);
        return uRepo.save(user);
    }

    public boolean checkEnoughMoney(User user, com.example.demo.entity.Service service){
        return user.getMoney() >= service.getPrice();
    }
}
