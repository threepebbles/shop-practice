package com.example.practiceShop.test;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HelloEntity {
    @Id
    @GeneratedValue
    @Column(name = "HELLO_ID")
    private Long id;

    private int age;
    private String name;

    private HelloEntity(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public static HelloEntity createHello(int age, String name) {
        if(age < 1) {
            throw new IllegalStateException("나이는 1살 이상이어야 합니다.");
        }
        if(!StringUtils.hasText(name)) {
            throw new IllegalStateException("이름이 입력되어야 합니다.");
        }

        return new HelloEntity(age, name);
    }
}
