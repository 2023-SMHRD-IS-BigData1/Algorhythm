package com.smhrd.algo.controller;

import com.smhrd.algo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "contact";
    }


}
