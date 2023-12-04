package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.WeatherResponse;
import com.smhrd.algo.repository.BikeStationRepository;
import com.smhrd.algo.service.UserService;
import com.smhrd.algo.service.WeatherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@RequiredArgsConstructor
@Log4j2
@Controller
public class MainController {

    private final UserService userService;
    @PostConstruct
    public void init() {
        userService.createUser("admin","1234","최윤석");
    }
    @GetMapping("/")
    public String login() {
        return "contact";
    }

    @GetMapping("/home")
    public String index() { return "index"; }

    @GetMapping("/myPage")
    public String myPage() {return "myPage";}

}
