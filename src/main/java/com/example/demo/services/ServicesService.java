package com.example.demo.services;

import com.example.demo.entity.Service;
import com.example.demo.entity.User;
import com.example.demo.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServicesService {
    private final ServiceRepository repository;

    @Autowired
    public ServicesService(ServiceRepository repository){
        this.repository = repository;
    }
    
    public Service saveService(Service service){
        return repository.save(service);
    }

    public List<Service> getALlServices(){
        return repository.findAll();
    }

    public Optional<Service> getById(Long id){
        return repository.findById(id);
    }

    public List<Service> getAllSortedByID(){
        List<Service> services = repository.findAll();
        services.sort((a, b) -> (int) (a.getId() - b.getId()));
        return services;
    }

    public List<Service> getAllSortedByName(){
        List<Service> services = repository.findAll();
        services.sort(Comparator.comparing(Service::getName));
        return services;
    }

    public List<Service> getAllSortedByPrice(){
        List<Service> services = repository.findAll();
        services.sort(Comparator.comparingInt(Service::getPrice));
        return services;
    }

    public List<Service> getAllSorted(String sort){
        List<Service> services;
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

    public Service getCheapestService(){
        List<Service> services = getALlServices();
        return services.stream()
                .min(Comparator.comparingInt(Service::getPrice))
                .orElseThrow(RuntimeException::new);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
    
    public boolean hasService(Service service){
        return repository.findByName(service.getName()).isPresent();
    }

    public List<Service> getServicesWithOutUser(List<Service> services, User user){
        return services
                .stream()
                .filter(accept -> !user.getServices().contains(accept))
                .collect(Collectors.toList());
    }
}
