package com.smhrd.algo.service;

import com.smhrd.algo.controller.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService{

    private final MySocialUserService mySocialUserService;

    @Autowired
    private  PrincipalDetails principalDetails;
    @Override
    public  OAuthUser loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User user = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> userInfo = user.getAttributes();

        String userId = "";
        String email = "";
        String name = "";

        switch(provider) {
            case "kakao":
                Map<String, Object> kakaoAccount = user.getAttribute("kakao_account");
                userId = provider + "_" + userInfo.get("id");
                email = (String) kakaoAccount.get("email");
                name = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");

        }
        log.info("provider: {} -> userId : {}, name : {}, email : {}",provider, userId, name, email);

        MyUser myUser = mySocialUserService.findUser(userId);
        if(myUser == null) {
            //회원가입 로직
            myUser = mySocialUserService.join(userId, name, email);
            log.info("joinId : {}", myUser.getUserId());
        }else {
            //로그인 로직
            log.info("not null : {}", myUser.getId());
        }
        return new PrincipalDetails(myUser, userInfo);
    }
}
