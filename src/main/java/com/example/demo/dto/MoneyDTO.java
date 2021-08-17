package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyDTO {
    @Min(value = 20, message = "минимальная сумма 20")
    private Integer moneyToAdd;
}

