package com.vti.auth_service.config;

import com.vti.auth_service.handle.OAuth2AuthenticationFailureHandler;
import com.vti.auth_service.handle.OAuth2AuthenticationSuccessHandler;
import com.vti.auth_service.model.Role;
import com.vti.auth_service.oauth2.respository.HttpCookieAuthrizationRepository;
import com.vti.auth_service.oauth2.service.CustomOAuth2USerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// Đánh dấu lớp cấu hình bảo mật
@Configuration
public class SecurityConfiguration {

    // Các URL được cho phép truy cập mà không cần xác thực
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh-token"
    };

    // Tiêm dịch vụ CustomOAuth2UserService
    @Autowired
    private CustomOAuth2USerService customOAuth2UserService;

    // Tiêm repository HttpCookieAuthorizationRequestRepository
    @Autowired
    private HttpCookieAuthrizationRepository httpCookieOAuthorizationRequestRepository;

    // Tiêm handler cho thành công xác thực OAuth2
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    // Tiêm handler cho thất bại xác thực OAuth2
    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    // Định nghĩa bean SecurityFilterChain để cấu hình bảo mật
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(WHITE_LIST_URL)
                            .permitAll()  // Cho phép truy cập không cần xác thực các URL trong WHITE_LIST_URL
                            .requestMatchers("/api/v1/accounts").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())
                            .anyRequest()
                            .authenticated();  // Các yêu cầu khác cần xác thực
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sử dụng quản lý phiên không trạng thái
                .oauth2Login()  // Cấu hình OAuth2 login
                .authorizationEndpoint()
                .baseUri("/oauth2/authorise")  // Đặt URI cơ bản cho điểm cuối ủy quyền
                .authorizationRequestRepository(httpCookieOAuthorizationRequestRepository)  // Sử dụng HttpCookieAuthorizationRequestRepository để lưu trữ yêu cầu ủy quyền
                .and()
                .redirectionEndpoint()
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)  // Đặt dịch vụ người dùng tùy chỉnh cho OAuth2
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)  // Đặt handler cho thành công xác thực
                .failureHandler(oAuth2AuthenticationFailureHandler);  // Đặt handler cho thất bại xác thực

        return http.build();  // Xây dựng SecurityFilterChain
    }

}
