package com.example.practiceShop.api.controller;

import com.example.practiceShop.api.service.MemberApiService;
import com.example.practiceShop.api.service.MemberApiService.JoinResponse;
import com.example.practiceShop.domain.auth.Auth;
import com.example.practiceShop.domain.auth.Auth.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberApiService memberApiService;

    @PostMapping("/member/new")
    @Auth(role = UserRole.GUEST)
    public JoinMemberResponse joinMember(@RequestBody @Valid JoinMemberRequest memberRequest) {
        JoinResponse joinResponse = memberApiService.join(memberRequest.name, memberRequest.email);

        return new JoinMemberResponse(
                joinResponse.getId(),
                joinResponse.getAccessToken(),
                joinResponse.getRefreshToken()
        );
    }

    //==DTO==//
    // @Data = @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
    // DTO는 그냥 레이어 간 주고 받는 데이터 값이므로 @Data를 사용해도 크게 상관 없음
    @Data
    @AllArgsConstructor
    public static class JoinMemberRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String email;
    }

    @Data
    @AllArgsConstructor
    public static class JoinMemberResponse {
        private Long id;
        private String accessToken;
        private String refreshToken;
    }
}
