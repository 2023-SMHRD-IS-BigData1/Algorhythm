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
public class TestController {

    private final UserService userService;
    private final BikeStationRepository bikeStationRepository;


    @GetMapping("/test")
    @ResponseBody
    public WeatherResponse weatherData() throws URISyntaxException {

        WeatherService weather = new WeatherService();
        String json = weather.getData();

        WeatherResponse response = weather.convertToObject(json);
        String category = response.getResponse().getBody().getItems().getItem().get(3).getCategory();
        Double temper = response.getResponse().getBody().getItems().getItem().get(3).getObsrValue();

        log.debug("category={}, temper={}", category, temper);

        return response;
    }

    @PostConstruct
    public void init() {

        log.debug(bikeStationRepository.findAll().size());

//        userService.createUser("admin","1234","최윤석");

    }

    @GetMapping("/logintest")
    public String login() {
        return "contact";
    }



}
