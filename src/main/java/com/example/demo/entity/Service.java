package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer price;
//    @ManyToMany
//    @JoinTable(
//            name = "user_service",
//            inverseJoinColumns = {@JoinColumn(name = "user_id")},
//            joinColumns = {@JoinColumn(name = "service_id")}
//    )
//    private List<User> users;
}
