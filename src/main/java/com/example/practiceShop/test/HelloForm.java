package com.example.practiceShop.test;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloForm {
    @NotEmpty(message = "이름 입력은 필수입니다.")
    private String name;

    @Min(value = 1, message = "나이는 1살 이상이어야 합니다.")
    private int age;
}
