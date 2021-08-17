package com.example.demo.entity;


import com.example.demo.dto.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_service",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "service_id")}
//    )
//    private List<Service> services;

//    public void addService(Service service){
//        services.add(service);
//    }

    public User(String name, String email, String password, String login, int money){
        this.email = email;
        this.name = name;
        this.password = password;
        this.login = login;
        this.money = money;
        this.role = Role.USER;
    }
}