package com.vti.auth_service.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;
import java.util.Base64;
import java.util.Optional;

// Lớp tiện ích để thao tác với cookie
public class CookieUtils {

    // Lấy cookie từ HttpServletRequest theo tên cookie
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    // Thêm cookie mới vào HttpServletResponse
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");  // Đặt đường dẫn của cookie
        cookie.setHttpOnly(true);  // Đặt cookie chỉ có thể truy cập qua HTTP
        cookie.setMaxAge(maxAge);  // Đặt thời gian sống của cookie
        response.addCookie(cookie);  // Thêm cookie vào phản hồi HTTP
    }

    // Xóa cookie từ HttpServletRequest và HttpServletResponse theo tên cookie
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");  // Đặt giá trị cookie là rỗng
                    cookie.setPath("/");  // Đặt đường dẫn của cookie
                    cookie.setMaxAge(0);  // Đặt thời gian sống của cookie là 0 để xóa cookie
                    response.addCookie(cookie);  // Thêm cookie vào phản hồi HTTP để xóa
                }
            }
        }
    }

    // Tuần tự hóa đối tượng thành chuỗi Base64
    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    // Giải tuần tự hóa chuỗi Base64 thành đối tượng
    @SuppressWarnings("deprecation")
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
