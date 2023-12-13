package com.smhrd.algo.controller.user;

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
            redirectAttributes.addFlashAttribute("message", true);
            return "redirect:/";

        } else {
            redirectAttributes.addFlashAttribute("message", false);
            return "redirect:/";
        }
    }

    @GetMapping()
    @ResponseBody
    public Boolean userIDIsDuplicate(@RequestParam String userId) {
        return userService.idDuplicateCheck(userId);
    }

    @PostMapping()
    public String userLoginCheck(@ModelAttribute UserDTO userDTO,
                                 RedirectAttributes redirectAttributes, HttpSession session) {

        User user = userService.loginUser(userDTO.getUserId(), userDTO.getUserPw());
        
        if (user != null) { // 로그인 성공 시 session에 저장
            session.setAttribute("user", user);

            if (user.getUserCode().equals("C")) {
                return "redirect:/home";
            } else {
                return "redirect:/dashboard";
            }

        } else { // 로그인 실패 시 실패 메시지 반환
            redirectAttributes.addFlashAttribute("message", false);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
