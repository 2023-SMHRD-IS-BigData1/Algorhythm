package com.smhrd.algo.service;
import com.smhrd.algo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuthUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuthUserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements PrincipalOAuth2UserService<OAuth2UserRequest, OAuth2User>{

    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpSession httpSession;
    @Override
    public  OAuthUser loadUser (OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 서비스 구분을 위한 작업 (구글 : google, naver)
        String 

        return null;
    }
}
