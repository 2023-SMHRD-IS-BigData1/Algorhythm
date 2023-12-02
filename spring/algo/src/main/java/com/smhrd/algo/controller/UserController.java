package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.LoginRequest;
import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/user/join")
    public String userJoin(@RequestParam String userId, @RequestParam String userPw, @RequestParam String userNickname) {
        log.info("New user registration: userId={}, userNickname={}", userId, userNickname);

        // 회원가입 정보를 받아와서 userService.createUser를 호출하여 사용자 생성 및 저장
        User newUser = userService.createUser(userId, userPw, userNickname);

        log.info("User registered successfully with userId={}", newUser.getUserId());

        // 회원가입 성공 시 로그인 페이지로 리다이렉트
        return "redirect:/login";  // "/login"은 로그인 페이지의 경로에 맞게 수정
    }

//    @PostMapping("/user/login")
//    public String userLogin(@RequestParam String userId, @RequestParam String userPw) {
//        User user = userService.loginUser(userId, userPw);
//        log.info("userId={}", userId);
//        return "index";
//    }


    @PostMapping("/user/login")
    public String userLoginCheck(@RequestParam String userId, @RequestParam String userPw, RedirectAttributes redirectAttributes) {
        // 로그인 시에는 사용자 아이디와 비밀번호를 함께 전달
        User user = userService.loginUser(userId, userPw);

        if (user != null) {
            log.info("User logged in successfully with userId={}", user.getUserId());
            redirectAttributes.addFlashAttribute("loginSuccess", true);
            return "redirect:/index";
        } else {
            log.info("Login failed for userId={}", userId);
            return "회원가입이 필요합니다";
        }

    }

}
