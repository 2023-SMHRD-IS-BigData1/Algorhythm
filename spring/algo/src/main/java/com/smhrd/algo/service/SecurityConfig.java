package com.smhrd.algo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private  final PrincipalOAuth2UserService principalOAuth2UserService;

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/","/home").permitAll() // 루트 및 /home 모든 사용자 허용
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                )
                // ... 다른 설정 ...
                .oauth2Login(httpSecurityOAuth2LoginCofigurer ->
                        httpSecurityOAuth2LoginCofigurer
                                .loginPage("/login")
                                .userInfoEndpoint()
                                .userService(principalOAuth2UserService)
                )
                .logout(?);
        return http.build();
    }

}
