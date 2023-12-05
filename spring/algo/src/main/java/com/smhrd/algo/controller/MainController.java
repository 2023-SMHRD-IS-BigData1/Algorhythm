package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.UserDTO;
import com.smhrd.algo.model.dto.WeatherResponse;
import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.repository.BikeStationRepository;
import com.smhrd.algo.service.UserService;
import com.smhrd.algo.service.WeatherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

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

}
