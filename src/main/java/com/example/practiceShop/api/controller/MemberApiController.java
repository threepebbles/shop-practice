package com.example.practiceShop.api.controller;

import com.example.practiceShop.api.service.MemberApiService;
import com.example.practiceShop.domain.auth.Auth;
import com.example.practiceShop.domain.auth.Auth.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
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
    public JoinMemberResponse joinMember(@RequestBody @Valid JoinMemberRequest request) {
        JoinMemberResponse response = memberApiService.join(request.name, request.email, request.password);

        return response;
    }

    // 자체 로그인
    @PostMapping("/member/login")
    @Auth(role = UserRole.GUEST)
    public LoginMemberResponse loginMember(@RequestBody @Valid LoginMemberRequest request) {
        LoginMemberResponse response = memberApiService.login(request.email, request.password);

        return response;
    }

    @PostMapping("/member/logout")
    @Auth(role = UserRole.MEMBER)
    public LogoutMemberResponse logoutMember(@RequestBody @Valid LogoutMemberRequest request) {
        memberApiService.logout(request.accessToken);

        // Exception 발생이 없었으면 여기까지 도달. 성공했다는 의미.
        return new LogoutMemberResponse(true);
    }

    //==DTO==//
    // @Data = @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
    // DTO는 그냥 레이어 간 주고 받는 데이터 값이므로 @Data를 사용해도 크게 상관 없음
    @Data
    public static class JoinMemberRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String email;
        @NotEmpty
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class JoinMemberResponse {
        private UUID id;
        // 가입과 동시에 로그인 되도록 하기 위해 토큰 반환
        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class LoginMemberRequest {
        // 로그인 폼
        @NotEmpty
        private String email;
        @NotEmpty
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class LoginMemberResponse {
        @NotEmpty
        private String accessToken;
        @NotEmpty
        private String refreshToken;
    }

    @Data
    public static class LogoutMemberRequest {
        @NotEmpty
        private String accessToken;
    }

    @Data
    @AllArgsConstructor
    public static class LogoutMemberResponse {
        private boolean isSuccess;
    }
}
