package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.WeatherResponse;
import com.smhrd.algo.model.entity.Bike;
import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.repository.BikeRepository;
import com.smhrd.algo.repository.UserRepository;
import com.smhrd.algo.service.UserService;
import com.smhrd.algo.service.WeatherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.time.LocalDate;

@RequiredArgsConstructor
@Log4j2
@Controller
public class TestController {

    private final UserService userService;
    private final BikeRepository bikeRepository;


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

//    @PostConstruct
//    public void init() {
//        // User 2명 가데이터 저장
//        userService.createUser("admin","1234","최윤석");
//
//        // 정거장 3곳 가데이터 저장
//    }
}
