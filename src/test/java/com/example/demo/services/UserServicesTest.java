package com.example.demo.services;

import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class UserServicesTest {
    @Autowired
    private UserService userService;

    @Test
    public void getUserByUsernameTest(){
        User user = userService.getUserByUsername("admin");
        Assertions.assertEquals(user.getName(), "Сергей Волков");
    }

    @Test
    public void isUserIsAdminTest(){
        Assertions.assertTrue(userService.isUserIsAdmin("admin"));
    }

    @Test
    public void isBlockedTest(){
        User user = userService.getUserByUsername("admin");
        Assertions.assertFalse(userService.isBlocked(user));
    }

    @Test
    public void hasUsernameTest(){
        Assertions.assertTrue(userService.hasUsername("admin"));
    }

    @Test
    public void hasEmailTest(){
        Assertions.assertTrue(userService.hasEmail("cjj200@gmail.com"));
    }
}
