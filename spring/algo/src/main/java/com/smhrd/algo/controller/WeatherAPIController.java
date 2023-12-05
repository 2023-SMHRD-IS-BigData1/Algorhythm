package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.WeatherResponse;
import com.smhrd.algo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@Log4j2
@RequiredArgsConstructor
@Controller
public class WeatherAPIController {
    private final WeatherService weatherService;


    @GetMapping("/test")
    @ResponseBody
    public WeatherResponse weatherData() throws URISyntaxException {

        String json = weatherService.getData();

        WeatherResponse response = weatherService.convertToObject(json);
        String category = response.getResponse().getBody().getItems().getItem().get(3).getCategory();
        Double temper = response.getResponse().getBody().getItems().getItem().get(3).getObsrValue();

        log.debug("category={}, temper={}", category, temper);

        return response;
    }

    @GetMapping("/weatherPredict")
    @ResponseBody
    public String weatherPredict() {
        return weatherService.sendWeather();
    }

    @GetMapping("/weather")
    @ResponseBody
    public String predictWhether() {
        return weatherService.getSevenDayData();
    }
}
