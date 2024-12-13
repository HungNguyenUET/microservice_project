package com.vti.auth_service.oauth2.user;

import java.util.Map;

public class FacebookOAuth2Info extends OAuth2UserInfo{
    // Constructor nhận vào một bản đồ (Map) chứa các thuộc tính người dùng
    public FacebookOAuth2Info(Map<String, Object> attributes) {
        super(attributes);
    }

    // Phương thức trả về ID của người dùng
    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    // Phương thức trả về tên của người dùng
    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    // Phương thức trả về email của người dùng
    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    // Phương thức trả về URL hình ảnh của người dùng
    @Override
    public String getImageUrl() {
        if(attributes.containsKey("picture")){
            Map<String,Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            if(pictureObj.containsKey("data")){
                Map<String,Object> dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")){
                    return (String) dataObj.get("url");
                }
            }
        }
        return (String) attributes.get("avatar_url");
    }
}
