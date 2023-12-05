package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.user.UserDTO;
import com.smhrd.algo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Log4j2
@Controller
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("userDTO",new UserDTO());
        return "login";
    }

    @GetMapping("/home")
    public String index() { return "index"; }

    @GetMapping("/myPage")
    public String myPage() {return "myPage";}

}
