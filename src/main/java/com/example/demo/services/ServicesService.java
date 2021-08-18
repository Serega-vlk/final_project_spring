package com.example.demo.services;

import com.example.demo.entity.Service;
import com.example.demo.repositories.ServiceRepositories;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServicesService {
    private ServiceRepositories repository;

    @Autowired
    public ServicesService(ServiceRepositories repository){
        this.repository = repository;
    }
    
    public Service saveService(Service service){
        return repository.save(service);
    }

    public List<Service> getALlServices(){
        return repository.findAll();
    }

    public Optional<Service> getById(int id){
        return repository.findById((long) id);
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
}
