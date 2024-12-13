package com.vti.auth_service.oauth2.service;

import com.vti.auth_service.model.User;
import com.vti.auth_service.oauth2.UserPrincipal;
import com.vti.auth_service.oauth2.entity.AuthProvider;
import com.vti.auth_service.oauth2.user.OAuth2UserInfo;
import com.vti.auth_service.oauth2.user.OAuth2UserInfoFactory;
import com.vti.auth_service.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service // Chú thích cho Spring rằng đây là một lớp dịch vụ (Service)
public class CustomOAuth2USerService extends DefaultOAuth2UserService {
    @Autowired // Tự động tiêm (inject) UserRepository vào lớp này
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Tải thông tin người dùng từ yêu cầu OAuth2
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            // Xử lý và trả về thông tin người dùng sau khi đã xử lý
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause()); // Ném ngoại lệ nếu có lỗi khác
        }
    }


    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        // Lấy thông tin người dùng từ OAuth2UserRequest và OAuth2User
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        Optional<User> userOptional = userRepository.findByUsername(oAuth2User.getName()); // Tìm người dùng theo username
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            // Kiểm tra xem provider của người dùng có khớp với provider trong request không
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationException(null, "Looks like you are signed up with " + user.getProvider()
                        + " account. Please use your " + user.getProvider() + " account to login");
            }
            user = updateExistingUser(user, oAuth2UserInfo); // Cập nhật thông tin người dùng hiện có
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo); // Đăng ký người dùng mới
        }

        return UserPrincipal.create(user); // Tạo UserPrincipal từ người dùng và trả về
    }


    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        // Tạo mới một đối tượng User và thiết lập các thuộc tính từ OAuth2UserInfo
        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail() != null ? oAuth2UserInfo.getEmail() : "");
        user.setImageurl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user); // Lưu người dùng mới vào cơ sở dữ liệu
    }

    private User updateExistingUser(User existUser, OAuth2UserInfo oAuth2UserInfo) {
        // Cập nhật thông tin người dùng hiện có
        existUser.setUsername(oAuth2UserInfo.getName());
        existUser.setImageurl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existUser); // Lưu người dùng đã cập nhật vào cơ sở dữ liệu
    }

}
