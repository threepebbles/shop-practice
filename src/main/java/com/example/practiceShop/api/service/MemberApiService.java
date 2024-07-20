package com.example.practiceShop.api.service;

import com.example.practiceShop.api.controller.MemberApiController.JoinMemberResponse;
import com.example.practiceShop.api.controller.MemberApiController.LoginMemberResponse;
import com.example.practiceShop.domain.auth.JwtProvider;
import com.example.practiceShop.domain.auth.RefreshToken;
import com.example.practiceShop.domain.auth.RefreshTokenRepository;
import com.example.practiceShop.domain.member.Member;
import com.example.practiceShop.domain.member.MemberService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberApiService {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redis;

    private final BCryptPasswordEncoder encoder;

    private final Long logoutTimeoutInSeconds = 1800L;

    @Transactional
    public JoinMemberResponse join(String name, String email, String password) {
        Member member = new Member(name, email, password);

        memberService.join(member);
        // access token, refresh token 발급
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());
        RefreshToken refreshTokenEntity = new RefreshToken(member, refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return new JoinMemberResponse(member.getId(), accessToken, refreshToken);
    }

    @Transactional
    public void logout(String accessToken) {
        Long memberId = jwtProvider.extractId(accessToken);
        Member member = memberService.findOne(memberId);
        if (member == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // refresh token 제거
        RefreshToken refreshToken = refreshTokenRepository.findOne(memberId);
        if (refreshToken != null) {
            refreshTokenRepository.remove(refreshToken);
        }

        // access token redis에 저장 (블랙리스트 용도)
        redis.opsForValue().set(accessToken, "", logoutTimeoutInSeconds, TimeUnit.SECONDS);
    }

    public LoginMemberResponse login(String email, String password) {
        List<Member> members = memberService.findByEmail(email);
        if (members.isEmpty()) {
            throw new IllegalArgumentException("가입 이력이 존재하지 않는 이메일입니다.");
        }
        Member member = members.get(0);
        // access token, refresh token 발급
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());
        RefreshToken refreshTokenEntity = new RefreshToken(member, refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return new LoginMemberResponse(accessToken, refreshToken);
    }
}