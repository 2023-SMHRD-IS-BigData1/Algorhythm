package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.weather.WeatherResponse;
import com.smhrd.algo.model.entity.WeatherInfo;
import com.smhrd.algo.repository.WeatherRepository;
import com.smhrd.algo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Log4j2
@RequiredArgsConstructor
@Controller
public class WeatherAPIController {
    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;

    @GetMapping("/test")
    @ResponseBody
    public WeatherResponse weatherData() throws URISyntaxException {

        String json = weatherService.getData();

        WeatherResponse response = weatherService.convertToObject(json);

        Double temper = response.getResponse().getBody().getItems().getItem().get(3).getObsrValue();
        Double rain = response.getResponse().getBody().getItems().getItem().get(2).getObsrValue();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        String formattedDate = now.format(formatter);

        WeatherInfo weather = new WeatherInfo(null, temper, rain, formattedDate);
        weatherRepository.save(weather);

        return response;
    }

    @GetMapping("/weatherPredict")
    @ResponseBody
    public String weatherPredict() {
        String temp = weatherService.getSevenDayTempData();
        String weather = weatherService.getSevenDayWeatherData();
        String twoWeather = weatherService.getTodayWeatherData();
        log.info(temp);
        return weatherService.sendWeather(temp, weather, twoWeather);
    }

    @GetMapping("/seven-day-temp")
    @ResponseBody
    public String sevenDayTemp() {
        return weatherService.getSevenDayTempData();
    }

    @GetMapping("/seven-day-weather")
    @ResponseBody
    public String sevenDayWeather() {
        return weatherService.getSevenDayWeatherData();
    }

    @GetMapping("/today-weather")
    @ResponseBody
    public String todayWeather() {
        return weatherService.getTodayWeatherData();
    }
}
