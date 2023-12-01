package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.LoginRequest;
import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/user/join")
    @ResponseBody
    public String userJoin(String userId, String userPw, String userName,
                           LocalDate userBirthdate, String userGender,
                           String userAddr) {
        log.info("userName={}", userName);
        return "ok";
    }

    @PostMapping("/user/login")
    public String userLogin(@RequestParam String userId,@RequestParam String userPw) {
        User user = userService.loginUser(userId, userPw);
        log.info("userId={}", userId);
        return "index";
    }


}