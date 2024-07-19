package com.example.practiceShop.test;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class HelloResponse {
    private String name;
    private int age;

    public static HelloResponse createHelloResponse(HelloEntity hello) {
        return HelloResponse.builder()
                .name(hello.getName())
                .age(hello.getAge())
                .build();
    }
}
