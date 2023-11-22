package com.smhrd.algo.service;

import com.smhrd.algo.model.dto.WeatherResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
public class WeatherService {

    private static final String URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

    public String getData() {
        /*
        기상청 단기예보 API를 활용하여 현재 시간의 날씨, 기온, 습도 등의 다양한 데이터를 가져옵니다.
        Params :
        Returns : JSON 형식의 String
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
        if (Integer.parseInt(minSdf.format(today))<40) {
            nowTime = Integer.parseInt(hourSdf.format(today))-1 + "00";
        } else {
            nowTime = hourSdf.format(today) + "00";
        }
        log.debug("hour={}", nowTime);

        // appKey
        String appKeyEncoded = "YE2mX8ly8vkTrh60IOcFkozbEWYKEEryEk7aZtWaRUliIzBPjap0KDOilHEiuufPtgNLe%2B%2FWtQ53JZ2doxIHZA%3D%3D";

        /*
        UriComponentsBuilder를 사용할 때, .build()를 실행할 때, 인코딩이 일어나며 이중 인코딩이 발생하게 된다.
        .build(true)를 사용하여 인코딩된 serviceKey를 그대로 통과 시켜야 오류를 막을 수 있다.
        */
        URI targetUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("serviceKey", appKeyEncoded)
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

//        // 자바 객체로 반환 받기
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(targetUrl, HttpMethod.GET,null, WeatherResponse.class);
//        log.debug("category={}",response.getBody().getResponse().getBody().getItems().getItem().get(3).getCategory());
//        log.debug("temp={}",response.getBody().getResponse().getBody().getItems().getItem().get(3).getObsrValue());

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
//            return response.getBody().toString();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
        }
    }
}
