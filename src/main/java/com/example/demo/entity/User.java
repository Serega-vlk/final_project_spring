package com.example.demo.entity;


import com.example.demo.dto.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"login", "email"}))
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "имя не может быть пустым")
    @Column(nullable = false)
    private String name;
    @Email(message = "невалидный email")
    @Column(nullable = false)
    @NotEmpty(message = "email не может быть пустым")
    private String email;
    @NotEmpty(message = "логин не может быть пустым")
    @Column(nullable = false)
    private String login;
    @Min(value = 0)
    private Integer money;
    @NotEmpty(message = "пароль не может быть пустым")
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault(value = "USER")
    private Role role;
    @Transient
    private String repeatPass;
    @ManyToMany
    @JoinTable(
            name = "user_service",
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            joinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services = new HashSet<>();

    public boolean addService(Service service){
        return services.add(service);
    }

    public boolean deleteService(Service service){
        return services.remove(service);
    }

    public User(String name, String email, String password, String login, int money){
        this.email = email;
        this.name = name;
        this.password = password;
        this.login = login;
        this.money = money;
        this.role = Role.USER;
    }
}