package com.example.practiceShop.test;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository helloRepository;

    /**
     * 엔티티 저장
     */
    @Transactional
    public Long save(HelloEntity hello) {
        helloRepository.save(hello);
        return hello.getId();
    }

    /**
     * 조회
     */
    public List<HelloEntity> findHellos() {
        return helloRepository.findAll();
    }

    public HelloEntity findById(Long id) {
        return helloRepository.findOne(id);
    }
}
