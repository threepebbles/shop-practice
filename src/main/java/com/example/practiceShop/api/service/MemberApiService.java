package com.example.practiceShop.api.service;

import com.example.practiceShop.domain.auth.JwtProvider;
import com.example.practiceShop.domain.auth.RefreshToken;
import com.example.practiceShop.domain.auth.RefreshTokenRepository;
import com.example.practiceShop.domain.member.Member;
import com.example.practiceShop.domain.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberApiService {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public JoinResponse join(String name, String email) {
        Member member = new Member(name, email);

        memberService.join(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());
        RefreshToken refreshTokenEntity = new RefreshToken(member, refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return new JoinResponse(member.getId(), accessToken, refreshToken);
    }

    //==DTO==//
    @Data
    @AllArgsConstructor
    public static class JoinResponse {
        private Long id;
        private String accessToken;
        private String refreshToken;
    }
}