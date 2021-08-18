package com.example.demo.repositories;

import com.example.demo.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceRepositories extends JpaRepository<Service, Long> {
    Optional<Service> findByName(String name);
}
