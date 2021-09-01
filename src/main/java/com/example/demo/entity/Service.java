package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Service {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "имя не указано")
    private String name;
    @Min(value = 0, message = "цена не может быть отрицательной")
    @NotNull(message = "цена не указана")
    private Integer price;
    @ManyToMany
    @JoinTable(
            name = "user_service",
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            joinColumns = @JoinColumn(name = "service_id")
    )
    private Set<User> users = new HashSet<>();

    public Service(String name, int price){
        this.name = name;
        this.price = price;
    }

    public void addUser(User user){
        users.add(user);
    }
}
