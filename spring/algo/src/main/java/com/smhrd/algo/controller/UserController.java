package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.user.UserDTO;
import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Fetch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String userJoin(@ModelAttribute UserDTO userDTO,
                           RedirectAttributes redirectAttributes) {

        // Id의 중복이 없다면
        if (userService.idDuplicateCheck(userDTO.getUserId())) {
            User newUser = userService.createUser(userDTO.getUserId(), userDTO.getUserPw(), userDTO.getUserNickname());
            redirectAttributes.addAttribute("message", true);
            return "redirect:/";

        } else {
            redirectAttributes.addAttribute("message", false);
            return "login";
        }
    }

    @PostMapping()
    public String userLoginCheck(@ModelAttribute UserDTO userDTO,
                                 RedirectAttributes redirectAttributes, HttpSession session) {

        User user = userService.loginUser(userDTO.getUserId(), userDTO.getUserPw());
        
        if (user != null) { // 로그인 성공 시 session에 저장
            session.setAttribute("user", user);
            return "home";

        } else { // 로그인 실패 시 실패 메시지 반환
            redirectAttributes.addAttribute("message", false);
            return "login";
        }
    }

//    @PostMapping("/ride")
//    public String updateUser(@ModelAttribute User user, double addKm) {
//        userService.updateUser(user, addKm);
//        return "redirect:/myPage";
//    }
    @GetMapping("/ride")
    @ResponseBody
    public User updateUser(@RequestParam String userId, double addKm) {
        return  userService.updateUser(userId, addKm);
    }
}
