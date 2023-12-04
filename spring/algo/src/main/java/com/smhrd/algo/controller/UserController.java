package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.LoginRequest;
import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public String userJoin(@RequestParam String userId, @RequestParam String userPw, @RequestParam String userName,
                           RedirectAttributes redirectAttributes) {

        // Id의 중복 여부 확인
        if (userService.idDuplicateCheck(userId)) {
            User newUser = userService.createUser(userId, userPw, userName);
            redirectAttributes.addAttribute("message", "success");
            return "redirect:/";

        } else {
            redirectAttributes.addAttribute("message", "fail");
            return "redirect:/";
        }
    }

    @GetMapping()
    public String userLoginCheck(@RequestParam String userId, @RequestParam String userPw,
                                 RedirectAttributes redirectAttributes, HttpSession session) {

        User user = userService.loginUser(userId, userPw);

        if (user != null) { // 로그인 성공 시 session에 저장
            session.setAttribute("User", user);
            return "redirect:/home";

        } else { // 로그인 실패 시 실패 메시지 반환
            redirectAttributes.addAttribute("message", "fail");
            return "redirect:/";
        }
    }
}
