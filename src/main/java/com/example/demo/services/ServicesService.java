package com.example.demo.services;

import com.example.demo.repositories.ServiceRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public List<com.example.demo.entity.Service> getAllSortedByID(){
        List<com.example.demo.entity.Service> services = repository.findAll();
        services.sort((a, b) -> (int) (a.getId() - b.getId()));
        return services;
    }

    public List<com.example.demo.entity.Service> getAllSortedByName(){
        List<com.example.demo.entity.Service> services = repository.findAll();
        services.sort(Comparator.comparing(com.example.demo.entity.Service::getName));
        return services;
    }

    public List<com.example.demo.entity.Service> getAllSortedByPrice(){
        List<com.example.demo.entity.Service> services = repository.findAll();
        services.sort(Comparator.comparingInt(com.example.demo.entity.Service::getPrice));
        return services;
    }

    public List<com.example.demo.entity.Service> getAllSorted(String sort){
        List<com.example.demo.entity.Service> services;
        switch (sort){
            case "id":
                services = getAllSortedByID();
                break;
            case "name":
                services = getAllSortedByName();
                break;
            case "price":
                services = getAllSortedByPrice();
                break;
            default:
                services = getALlServices();
        }
        return services;
    }
}
