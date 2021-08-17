package com.example.demo.services;

import com.example.demo.repositories.ServiceRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {
    private ServiceRepositories repository;

    @Autowired
    public ServicesService(ServiceRepositories repository){
        this.repository = repository;
    }

    public List<com.example.demo.entity.Service> getALlServices(){
        return repository.findAll();
    }
}
