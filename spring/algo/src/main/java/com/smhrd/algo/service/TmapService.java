package com.smhrd.algo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.algo.model.dto.PoiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Log4j2
public class TmapService {

    private  static final String URL = "https://apis.openapi.sk.com/tmap/pois";

    public String poiSearch(String searchKeyword) {
        /*
         Description : 검색어를 받아 관련 위치의 위도 경도를 반환합니다.
         Params      : 검색어 (String)
         Returns     : 검색결과 (JSON)
        */
        RestTemplate restTemplate = new RestTemplate();

        // appKey
        String appKey = "nRUNxPRv9p2mYIfwpEZPC7qHQMtNN5ZB25T3ErVd";

        // URI 생성
        URI targetUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("appKey", appKey)
                .queryParam("version", 1)
                .queryParam("format", "json")
                .queryParam("callback", "result")
                .queryParam("searchKeyword", searchKeyword)
                .queryParam("reqCoordType", "WGS84GEO")
                .queryParam("resCoordType", "WGS84GEO")
                .queryParam("areaLLCode", 29)
                .encode()
                .build()
                .toUri();

        log.debug("searchKeyword={}", searchKeyword);
        log.debug("url={}", targetUrl);

        ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
           return response.getBody();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
        }
    }

    public PoiResponse convertToObject(String json) {
        /*
         Description : PoiSearch Api로 받은 데이터를 자바 객체화 합니다.
         Params      : JSON 형태의 문자열
         Returns     : PoiResponse (PoiSearch Api 자바객체)
        */
        ObjectMapper mapper = new ObjectMapper();
        PoiResponse object = null;

        try {
            object = mapper.readValue(json, PoiResponse.class);
        } catch (IOException e) {
            log.debug("Failed to parse WeatherResponse from JSON", e);
        }
        return object;
    }

}
