package com.vti.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Đánh dấu lớp cấu hình Web MVC
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // Đặt thời gian sống tối đa cho CORS (3600 giây = 1 giờ)
    private final long MAX_AGE_SECS = 3600;

    // Đọc thuộc tính allowedOrigins từ file cấu hình ứng dụng
    @Value("${app.cors.allowedOrigins}")
    private String[] allowedOrigins;

    // Ghi đè phương thức addCorsMappings để cấu hình CORS cho ứng dụng
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Áp dụng cấu hình CORS cho tất cả các đường dẫn
                .allowedOrigins(allowedOrigins)  // Chỉ định các nguồn (origins) được phép
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")  // Chỉ định các phương thức HTTP được phép
                .allowedHeaders("*")  // Cho phép tất cả các tiêu đề (headers)
                .allowCredentials(true)  // Cho phép gửi thông tin xác thực (credentials)
                .maxAge(MAX_AGE_SECS);  // Đặt thời gian sống tối đa cho CORS
    }
}
