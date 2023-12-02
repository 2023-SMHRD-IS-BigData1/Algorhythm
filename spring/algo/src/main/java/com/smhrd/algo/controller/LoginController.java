package com.smhrd.algo.controller;

import com.smhrd.algo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "contact";
    }



    @GetMapping("/index")
    public String showIndexPage(Model model) {
        // 여기서는 "index"라는 뷰를 보여주도록 설정
        if (model.containsAttribute("loginSuccess")) {
            // 로그인 성공 시의 처리
            // 예를 들어, 로그인 성공 메시지를 뷰에 전달하거나 추가적인 작업을 수행할 수 있습니다.
            model.addAttribute("loginMessage", "로그인에 성공했습니다.");
        }
        return "index";
    }


    @GetMapping("/gallery")
    public String showGalleryPage() {
        // "index"라는 뷰를 보여주도록 설정
        return "gallery";
    }
}
