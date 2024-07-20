package com.example.practiceShop.auth;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final EntityManager em;

    public Long save(RefreshToken refreshToken) {
        em.persist(refreshToken);
        return refreshToken.getId();
    }

    public RefreshToken findOne(Long id) {
        return em.find(RefreshToken.class, id);
    }

    public List<RefreshToken> findAll() {
        return em.createQuery("select r from RefreshToken r", RefreshToken.class)
                .getResultList();
    }

    public List<RefreshToken> findByJwt(String jwt) {
        return em.createQuery("select r from RefreshToken r where r.refreshToken = :jwt", RefreshToken.class)
                .setParameter("jwt", jwt)
                .getResultList();
    }
}
