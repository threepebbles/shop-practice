package com.example.practiceShop;

import com.example.practiceShop.test.HelloEntity;
import com.example.practiceShop.test.HelloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PracticeShopApplicationTests {

	@Autowired
	private HelloService helloService;

	@Test
	void databaseLoads() {
		HelloEntity helloEntity = HelloEntity.createHello(1, "이름");
		helloService.save(helloEntity);
	}
}
