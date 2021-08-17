package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO {
    private String oldPassword;
    @NotEmpty
    @Size(min = 5, max = 15, message = "пароль должен одержать он 5 до 15 символов")
    private String newPassword;
}
