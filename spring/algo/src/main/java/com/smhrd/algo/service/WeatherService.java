package com.smhrd.algo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherService {

    private static final String URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

    public String getData() {

        RestTemplate restTemplate = new RestTemplate();

        // 날짜 간략화
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        String todayDate = sdf.format(today);

        // 시간 간략화
        SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
        String nowTime = hourSdf.format(today)+"00";

        // appKey
        String appKey = "YE2mX8ly8vkTrh60IOcFkozbEWYKEEryEk7aZtWaRUliIzBPjap0KDOilHEiuufPtgNLe%2B%2FWtQ53JZ2doxIHZA%3D%3D";

        URI targetUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("serviceKey", appKey)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", todayDate)
                .queryParam("base_time", nowTime)
                .queryParam("nx", 66)
                .queryParam("ny", 103)
                .build()
                .encode()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
        }

    }

}
