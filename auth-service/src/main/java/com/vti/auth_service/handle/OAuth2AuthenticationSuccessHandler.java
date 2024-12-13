package com.vti.auth_service.handle;

import com.vti.auth_service.oauth2.UserPrincipal;
import com.vti.auth_service.oauth2.respository.HttpCookieAuthrizationRepository;
import com.vti.auth_service.user.services.JwtService;
import com.vti.auth_service.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

// Đánh dấu lớp là một Spring Component
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // Tiêm dịch vụ JwtService
    private final JwtService jwtService;

    // Tiêm repository HttpCookieAuthorizationRepository
    private final HttpCookieAuthrizationRepository httpCookieAuthrizationRepository;

    // Ghi đè phương thức onAuthenticationSuccess để xử lý khi xác thực OAuth2 thành công
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication); // Xác định URL đích
        if (response.isCommitted()) {
            return;
        }

        clearAuthenticationAttributes(request, response); // Xóa các thuộc tính xác thực
        getRedirectStrategy().sendRedirect(request, response, targetUrl); // Chuyển hướng đến URL đích
    }

    // Phương thức xác định URL đích sau khi xác thực thành công
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieAuthrizationRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtService.generateAccessToken(userPrincipal);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toString();
    }

    // Phương thức xóa các thuộc tính xác thực sau khi xác thực thành công
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieAuthrizationRepository.removeAuthorizationRequestCookies(request, response);
    }
}
