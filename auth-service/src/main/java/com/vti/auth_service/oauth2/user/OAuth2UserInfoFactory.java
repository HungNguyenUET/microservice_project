package com.vti.auth_service.oauth2.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {
    // Phương thức tĩnh để lấy thông tin người dùng OAuth2 dựa trên registrationId
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes){
        log.info("Attributes User authenticated **********************");
        log.info("attributes: {}", attributes);

        // Kiểm tra registrationId và trả về đối tượng OAuth2UserInfo tương ứng
        if(registrationId.equalsIgnoreCase("google")){
            return new GoogleOAuth2Info(attributes);
        }
        else if(registrationId.equalsIgnoreCase("facebook")){
            return new FacebookOAuth2Info(attributes);
        }
        else if(registrationId.equalsIgnoreCase("github")){
            return new GithubOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2AuthenticationException(null,"Sorry ! login with "+ registrationId+ " is not supported yet");
        }
    }
}
