package com.example.practiceShop.interceptor;

import com.example.practiceShop.auth.Auth;
import com.example.practiceShop.auth.Auth.UserRole;
import com.example.practiceShop.auth.JwtProvider;
import com.example.practiceShop.exception.LoggedOutAccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("intercept from {}", request.getRequestURL().toString());

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        // 권한에 대한 어노테이션이 없는 경우(인증이 필요 없는 API)
        if (auth == null) {
            // 통과
            return true;
        }

        // 멤버 권한인 경우
        if (auth.role().equals(UserRole.MEMBER)) {
            validateMemberAuth(extractCookiesFromCurrentRequestHeader());
            return true;
        } else if (auth.role().equals(UserRole.ADMIN)) {
            validateAdminAuth(extractCookiesFromCurrentRequestHeader());
            return true;
        }

        // 이외의 경우 거절
        return false;
    }

    private void validateMemberAuth(Cookie[] cookies) {
        Cookie cookie = extractCookieByName(cookies, jwtProvider.accessTokenSubject);

        if (cookie == null) {
            throw new IllegalStateException("access token이 존재하지 않습니다.");
        }

        String accessToken = cookie.getValue();
        jwtProvider.validateAccessToken(accessToken);

        String isLoggedOut = redis.opsForValue().get(accessToken);
        if (isLoggedOut != null) {
            throw new LoggedOutAccessToken("로그아웃된 access token입니다.");
        }
    }

    private void validateAdminAuth(Cookie[] cookies) {
//        Cookie cookie = extractCookieByName(cookies, jwtProvider.accessTokenSubject);
//        if (cookie == null) {
//            throw new IllegalStateException("access token이 존재하지 않습니다.");
//        }
//
//        String accessToken = cookie.getValue();
//        jwtProvider.validateAccessToken(accessToken);
    }

    private Cookie[] extractCookiesFromCurrentRequestHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getCookies();
    }

    private Cookie extractCookieByName(Cookie[] cookies, String cookieName) {
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}