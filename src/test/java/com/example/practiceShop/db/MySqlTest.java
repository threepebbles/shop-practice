package com.example.practiceShop.db;

import com.example.practiceShop.test.HelloEntity;
import com.example.practiceShop.test.HelloRepository;
import com.example.practiceShop.test.HelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MySqlTest {
    @Autowired
	private HelloService helloService;

    @Autowired
    private HelloRepository helloRepository;

    @Test
    void setHelloServiceTest() {
		HelloEntity helloEntity = HelloEntity.createHello(1, "이름");
		Long id = helloService.save(helloEntity);

        HelloEntity findHello = helloRepository.findOne(id);
        Assertions.assertThat(findHello.getAge()).isEqualTo(1);
        Assertions.assertThat(findHello.getName()).isEqualTo("이름");
    }
}
