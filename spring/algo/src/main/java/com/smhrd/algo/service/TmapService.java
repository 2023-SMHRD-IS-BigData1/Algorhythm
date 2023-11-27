package com.smhrd.algo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.algo.model.dto.LatLonRequest;
import com.smhrd.algo.model.dto.LatLonRequest.LatlonList.Latlon;
import com.smhrd.algo.model.dto.NaviPersonResponse;
import com.smhrd.algo.model.dto.NaviPersonResponse.Feature;
import com.smhrd.algo.model.dto.PoiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class TmapService {

    public String poiSearch(String searchKeyword) {
        /*
         Description : 검색어를 받아 관련 위치의 위도 경도를 반환합니다.
         Params      : 검색어 (String)
         Returns     : 검색결과 (JSON)
        */
        RestTemplate restTemplate = new RestTemplate();

        // appKey, URL
        String appKey = "nRUNxPRv9p2mYIfwpEZPC7qHQMtNN5ZB25T3ErVd";
        String URL = "https://apis.openapi.sk.com/tmap/pois";

        // URI 생성
        URI targetUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("appKey", appKey)
                .queryParam("version", 1)
                .queryParam("format", "json")
                .queryParam("callback", "result")
                .queryParam("searchKeyword", searchKeyword)
                .queryParam("count", 10)
                .queryParam("multiPoint", "Y")
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

    public String naviPerson(LatLonRequest latlon) {
        /*
         Description : 출발지와 도착지의 위도와 경도를 입력받아,
                       보행자도로 기준으로 길을 찾아줍니다.
         Params      : 출발지명과 위도경도, 도착지명과 위도경도
         Returns     : 보행자 도로 기준 길 JSON 데이터
        */
        RestTemplate restTemplate = new RestTemplate();

        // appKey, URL
        String appKey = "nRUNxPRv9p2mYIfwpEZPC7qHQMtNN5ZB25T3ErVd";
        String URL = "https://apis.openapi.sk.com/tmap/routes/pedestrian";

        // header 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("appKey", appKey);
        headers.set("version", "1");
        headers.set("callback", "function");

        // passList 제작
        log.debug(latlon.getLatLonString());
        String passList = null;
        List<Latlon> data = latlon.getLatlonList().getLatlon();

        // body 세팅
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("startX", data.get(0).getLon());
        map.put("startY", data.get(0).getLat());
        map.put("endX", data.get(1).getLon());
        map.put("endY", data.get(1).getLat());
        if (passList != null) {map.put("passList", passList);}
        map.put("speed", 20);
        map.put("searchOption", "30");
        map.put("startName", "출발");
        map.put("endName", "도착");

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

    public NaviPersonResponse converToNaviObject(String json) {
         /*
         Description : 보행자경로 Api로 받은 데이터를 자바 객체화 합니다.
         Params      : JSON 형태의 문자열
         Returns     : NaviPersonResponse (NaviPersonResponse Api 자바객체)
        */
        ObjectMapper mapper = new ObjectMapper();
        NaviPersonResponse object = null;

        try {
            object = mapper.readValue(json, NaviPersonResponse.class);

        } catch (IOException e) {
            log.debug("Failed to parse WeatherResponse from JSON", e);
        }
        return object;
    }

    public LatLonRequest findStation(NaviPersonResponse object) {
        /*
         Description : Data에서 Point의 위경도만을 추출하여 근처의 정거장을 찾습니다.
         Params      : NaviPersonResponse 출발지와 목적지로 추출된 결과
         Returns     : LatLonRequest 경유지가 포함된 LatLonRequest
        */

        List<Feature> feature = object.getFeatures();

//        for(int i=0; i<feature.size(); i++) {
//            if (feature.get(i).getGeometry().getType())
//        }


        return null;
    }
}
