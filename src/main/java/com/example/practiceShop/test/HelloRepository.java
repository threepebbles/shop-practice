package com.example.practiceShop.test;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HelloRepository {
    private final EntityManager em;

    public Long save(HelloEntity hello) {
        em.persist(hello);
        return hello.getId();
    }

    public HelloEntity findOne(Long id) {
        return em.find(HelloEntity.class, id);
    }

    public List<HelloEntity> findAll() {
        return em.createQuery("select h from HelloEntity h", HelloEntity.class)
                .getResultList();
    }

    public List<HelloEntity> findByName(String name) {
        // :는 변수 바인딩 문법. setParameter을 통해 동적으로 값 설정 가능
        return em.createQuery("select h from HelloEntity h where h.name = :helloName", HelloEntity.class)
                .setParameter("helloName", name)
                .getResultList();
    }
}
