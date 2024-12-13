package com.vti.auth_service.oauth2.respository;

import com.vti.auth_service.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
// Lớp này triển khai AuthorizationRequestRepository để quản lý OAuth2AuthorizationRequest thông qua cookie
public class HttpCookieAuthrizationRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    // Tên cookie chứa yêu cầu ủy quyền OAuth2
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";

    // Tên cookie chứa URI chuyển hướng sau khi đăng nhập
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME= "redirect_uri";

    // Thời gian sống của cookie tính bằng giây
    private static final int cookieExpireSeconds = 180;


    // Tải yêu cầu ủy quyền từ cookie
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie,OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    // Lưu yêu cầu ủy quyền vào cookie
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if(authorizationRequest ==null){
            CookieUtils.deleteCookie(request,response,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtils.deleteCookie(request,response,REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }

        CookieUtils.addCookie(response,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest),
                cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if(StringUtils.isNotBlank(redirectUriAfterLogin)){
            CookieUtils.addCookie(response,REDIRECT_URI_PARAM_COOKIE_NAME,redirectUriAfterLogin,cookieExpireSeconds);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    // Xóa yêu cầu ủy quyền từ cookie
    public void removeAuthorizationRequestCookies(HttpServletRequest request,HttpServletResponse response){
        CookieUtils.deleteCookie(request,response,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request,response,REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}
