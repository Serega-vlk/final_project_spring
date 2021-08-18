package com.example.demo.services;

import com.example.demo.entity.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ServiceServiceTest {
    @Autowired
    private ServicesService serviceService;

    @Test
    public void getCheapestServiceTest(){
        Service service = serviceService.getCheapestService();
        Assertions.assertEquals(service.getPrice(), 60);
    }
}