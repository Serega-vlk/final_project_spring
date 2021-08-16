package com.example.demo.entity;


import com.example.demo.dto.Role;
import com.example.demo.dto.Status;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "пароль не может быть пустым")
    @Column(nullable = false)
    //@Size(min = 5, max = 15, message = "пароль должен содержать от 5 до 15 символов")
    private String password;
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault(value = "USER")
    private Role role;
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault(value = "ACTIVE")
    private Status status;
    @Transient
    private String repeatPass;

    public User(String name, String email, String password, String login){
        this.email = email;
        this.name = name;
        this.password = password;
        this.login = login;
        this.role = Role.USER;
        this.status = Status.ACTIVE;
    }
}