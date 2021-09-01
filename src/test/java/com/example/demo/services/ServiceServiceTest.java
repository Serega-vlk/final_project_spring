package com.example.demo.services;

import com.example.demo.entity.Service;
import com.example.demo.repositories.ServiceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class ServiceServiceTest {
    @Autowired
    private ServicesService serviceService;
    @Autowired
    private UserService userService;
    @Autowired
    private ServiceRepository repository;
    private Service service = new Service("test", 1);

    @BeforeEach
    public void addService(){
        repository.save(service);
    }

    @AfterEach
    public void deleteService(){
        repository.delete(service);
    }

    @Test
    public void getCheapestServiceTest(){
        Assertions.assertEquals(serviceService.getCheapestService().getPrice(), 1);
    }
}
