package com.example.practiceShop.domain.auth;

import com.example.practiceShop.domain.member.Member;
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

    public List<RefreshToken> findByMember(Member member) {
        return em.createQuery("select r from RefreshToken r where r.member = :member", RefreshToken.class)
                .setParameter("member", member)
                .getResultList();
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

    public void remove(RefreshToken refreshToken) {
        em.remove(refreshToken);
    }
}
