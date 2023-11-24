/*
package com.smhrd.algo.service;
import com.smhrd.algo.model.dto.Role;
import com.smhrd.algo.model.entity.SnsUser;
import com.smhrd.algo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuthUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuthUserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        String registraiontId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserId();

        String email;
        Map<String, Object> response = OAuth2User.getAttributes();

        if(registraiontId.equals("naver")){
            Map<String, Object> hash = (Map<String, Object>) rsponse.get(response);
            email = (String) hash.get("email");
        }else if(registraiontId.equals("google")){
            email = (String) response.get("email");
        }else {
            throw  new Oauth2AuthenticationException("허용되지 않는 인증입니다.");
        }

        SnsUser snsUser;
        Optional<SnsUser> optionalSnsUser = userRepository.findByEmail(email);

        if(optionalSnsUser.isPresent()){
            snsUser = new SnsUser();
            snsUser.setEmail(email);
            snsUser.setRole(Role.ROLE_USER);
            userRepository.save(snsUser);
        }
        httpSession.setAttribute("snsUser", snsUser);
        return new DefaultOAuth2User(
                Collection.singleton(new SimpleGrantedAuthority(snsUser.getRole().toString()))
                , oAuth2User.getAttributes()
                , userNameAttributeName);
    }
}
*/
