package com.smhrd.algo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.algo.model.dto.weather.WeatherResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Service
public class WeatherService {


    public String sendWeather(String temp, String weather) {
        /*
        Description : Pycharm으로 데이터를 전송하여 머신러닝을 실시한 뒤, 값을 돌려 받습니다..
        Params      :
        Returns     : JSON 형식의 String
        */

        RestTemplate restTemplate = new RestTemplate();

        // URL
        String URL = "http://127.0.0.1:5000/predict";

        // header 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // body 세팅
        ObjectMapper mapper = new ObjectMapper();

        List<String> data = new ArrayList<>();
        data.add(temp);
        data.add(weather);

        Map<String, Object> map = new HashMap<>();
        map.put("json", data);

        String jsonBody = null;
        try {
            jsonBody = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // combine
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    public String getData() {
        /*
        Description : 기상청 단기예보 API를 활용하여 현재 시간의 날씨, 기온, 습도 등의 다양한 데이터를 가져옵니다.
        Params      :
        Returns     : JSON 형식의 String
        */

        RestTemplate restTemplate = new RestTemplate();

        // 날짜 간략화
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        String todayDate = sdf.format(today);

        // 시간 간략화
        SimpleDateFormat minSdf = new SimpleDateFormat("mm");
        SimpleDateFormat hourSdf = new SimpleDateFormat("HH");

        String nowTime = null;
        if (Integer.parseInt(minSdf.format(today))<41) {
            if (Integer.parseInt(hourSdf.format(today))-1<11){
                nowTime = "0" + (Integer.parseInt(hourSdf.format(today))-1) + "00";
            } else {
                nowTime = (Integer.parseInt(hourSdf.format(today))-1) + "00";
            }
        } else {
                nowTime = hourSdf.format(today) + "00";
        }
        log.debug("hour={}", nowTime);

        // appKey
        String appKey = "YE2mX8ly8vkTrh60IOcFkozbEWYKEEryEk7aZtWaRUliIzBPjap0KDOilHEiuufPtgNLe%2B%2FWtQ53JZ2doxIHZA%3D%3D";

        /*
        UriComponentsBuilder를 사용할 때, .build()를 실행할 때, 인코딩이 일어나며 이중 인코딩이 발생하게 된다.
        .build(true)를 사용하여 인코딩된 serviceKey를 그대로 통과 시켜야 오류를 막을 수 있다.
        */

        String URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

        URI targetUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("serviceKey", appKey)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", todayDate)
                .queryParam("base_time", nowTime)
                .queryParam("nx", 66)
                .queryParam("ny", 103)
                .build(true)
                .encode()
                .toUri();

        log.debug("url={}", targetUrl);

        // String으로 반환 받기
        ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
        }
    }

    public String getTodayWeatherData() {
        /*
        Description : openweathermap API를 활용하여 5일간의 날씨, 기온, 습도 등의 다양한 데이터를 가져옵니다.
        Params      :
        Returns     : JSON 형식의 String
        */

        RestTemplate restTemplate = new RestTemplate();

        // appKey
        String appKey = "d5f9fcd2ba2b9e1b3594c167745c4849";

        /*
        UriComponentsBuilder를 사용할 때, .build()를 실행할 때, 인코딩이 일어나며 이중 인코딩이 발생하게 된다.
        .build(true)를 사용하여 인코딩된 serviceKey를 그대로 통과 시켜야 오류를 막을 수 있다.
        */

        String URL = "http://api.openweathermap.org/data/2.5/forecast";

        URI targetUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("appid", appKey)
                .queryParam("lat", 36.5040736)
                .queryParam("lon", 127.2494855)
                .queryParam("units", "metric")
                .build(true)
                .encode()
                .toUri();

        log.debug("url={}", targetUrl);

        // String으로 반환 받기
        ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
        }
    }

    public WeatherResponse convertToObject(String json) {
        /*
         Description : 날씨 API로 받은 데이터를 자바 객체화 합니다.
         Params      : JSON 형태의 문자열
         Returns     : WeatherResponse (날씨 API 자바객체)
        */
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse object = null;

        try {
            object = mapper.readValue(json, WeatherResponse.class);
        } catch(IOException e) {
            log.debug("Failed to parse WeatherResponse from JSON", e);
        }
        return object;
    }

    public String getSevenDayTempData() {
        /*
        Description : 기상청 중기예보 API를 활용하여 3일 뒤부터 7일간의 기온 데이터를 가져옵니다.
        Params      :
        Returns     : JSON 형식의 String
        */

        RestTemplate restTemplate = new RestTemplate();

        // 날짜 간략화
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String predictDay = LocalDate.now().format(formatter) + "0600";

        // 시간 간략화
        String nowTime = null;

        // appKey
        String appKey = "LG88A4t0E5rRdYGvW4%2BwQXnaX2ksSQ6KeA8dzlOj%2FnV6KxB0rpfA5zPeyo0mdcf%2Fe%2BoSCd%2Bq7pFjtvAoPn2zNg%3D%3D";

        /*
        UriComponentsBuilder를 사용할 때, .build()를 실행할 때, 인코딩이 일어나며 이중 인코딩이 발생하게 된다.
        .build(true)를 사용하여 인코딩된 serviceKey를 그대로 통과 시켜야 오류를 막을 수 있다.
        */
        URI targetUrl = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa")
                .queryParam("serviceKey", appKey)
                .queryParam("dataType", "JSON")
                .queryParam("regId", "11C20404")
                .queryParam("tmFc", predictDay)
                .build(true)
                .encode()
                .toUri();

        log.debug("url={}", targetUrl);

        // String으로 반환 받기
        ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
        }
    }

    public String getSevenDayWeatherData() {
        /*
        Description : 기상청 중기예보 API를 활용하여 3일 뒤부터 7일간의 날씨 데이터를 가져옵니다.
        Params      :
        Returns     : JSON 형식의 String
        */

        RestTemplate restTemplate = new RestTemplate();

        // 날짜 간략화
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String predictDay = LocalDate.now().format(formatter) + "0600";

        // 시간 간략화
        String nowTime = null;

        // appKey
        String appKey = "LG88A4t0E5rRdYGvW4%2BwQXnaX2ksSQ6KeA8dzlOj%2FnV6KxB0rpfA5zPeyo0mdcf%2Fe%2BoSCd%2Bq7pFjtvAoPn2zNg%3D%3D";

        /*
        UriComponentsBuilder를 사용할 때, .build()를 실행할 때, 인코딩이 일어나며 이중 인코딩이 발생하게 된다.
        .build(true)를 사용하여 인코딩된 serviceKey를 그대로 통과 시켜야 오류를 막을 수 있다.
        */
        URI targetUrl = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst")
                .queryParam("serviceKey", appKey)
                .queryParam("dataType", "JSON")
                .queryParam("regId", "11C20000")
                .queryParam("tmFc", predictDay)
                .build(true)
                .encode()
                .toUri();

        log.debug("url={}", targetUrl);

        // String으로 반환 받기
        ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
        }
    }
}
