package com.smhrd.algo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.algo.model.dto.LatLonRequest;
import com.smhrd.algo.model.dto.LatLonRequest.LatlonList;
import com.smhrd.algo.model.dto.LatLonRequest.LatlonList.Latlon;
import com.smhrd.algo.model.dto.LatLonRequest.LatlonList.LatlonStation;
import com.smhrd.algo.model.dto.NaviPersonResponse;
import com.smhrd.algo.model.dto.NaviPersonResponse.Feature;
import com.smhrd.algo.model.dto.NaviPersonResponse.Feature.Geometry;
import com.smhrd.algo.model.dto.PoiResponse;
import com.smhrd.algo.model.entity.BikeStation;
import com.smhrd.algo.repository.BikeStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TmapService {

    private final BikeStationRepository bikeStationRepository;

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
                .queryParam("areaLLCode", 36)
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

        // passList 존재여부 체크
        String passList = null;
        try {
            if (!latlon.getLatlonList().getLatlonStations().isEmpty()) {
                passList = latlon.getLatLonStationString();
            }
        } catch (NullPointerException e) {
            passList = null;
        }
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

        List<Feature> features = object.getFeatures();
        List<List<Double>> latlonList = new ArrayList<>();

        // 보행자 경로의 Point 객체만 반환받아 List로 만들기
        for (int i=0; i<features.size(); i++) {
            Geometry data = features.get(i).getGeometry();
            if (data.getType().equals("Point")){
                latlonList.add((List<Double>) data.getCoordinates()); // List<Double> [0] lon, [1] lat
            }
        }

        List<BikeStation> stations = bikeStationRepository.findAll();

        // 출발지와 가까운 대여 정거장 찾기
        BikeStation nearestStationStart = null;
        for (List<Double> targetStation : latlonList) {
            double tempDistance = 500;
            for (BikeStation station : stations) {

                // 거리 계산
                double distance = calculateDistance(targetStation.get(1), targetStation.get(0),
                        station.getLat().doubleValue(), station.getLng().doubleValue());

                // 거리가 500m 보다 작다면 정거장 저장
                if (distance <= 500) {
                    if (distance < tempDistance) {
                        tempDistance = distance;
                        nearestStationStart = station;
                    }
                }
            }

            // 포인트와 가까운 정류장이 하나라도 존재하면 break;
            if (nearestStationStart != null) {
                log.debug("stationStart={}, distance={}", nearestStationStart, tempDistance);
                break;
            }
        }


        // 목적지와 가까운 반납 정거장 찾기
        BikeStation nearestStationEnd = null;
        for (int i=latlonList.size()-1; i>=0; i--) {
            double tempDistance = 500;
            for (BikeStation station : stations) {

                // 거리 계산
                double distance = calculateDistance(latlonList.get(i).get(1), latlonList.get(i).get(0),
                        station.getLat().doubleValue(), station.getLng().doubleValue());

                // 거리가 500m 보다 작다면 정거장 저장
                if (distance <= 500) {
                    if (distance < tempDistance) {
                        tempDistance = distance;
                        nearestStationEnd = station;
                    }
                }
            }

            // 포인트와 가까운 정류장이 하나라도 존재하면 break;
            if (nearestStationStart != null) {
                log.debug("stationEnd={}, distance={}", nearestStationEnd, tempDistance);
                break;
            }
        }

        // LatLonRequest Build
        List<Latlon> latlons = new ArrayList<>();
        latlons.add(Latlon.builder()
                .type("출발")
                .lat(latlonList.get(0).get(1))
                .lon(latlonList.get(0).get(0))
                .build());
        latlons.add(Latlon.builder()
                .type("도착")
                .lat(latlonList.get(latlonList.size()-1).get(1))
                .lon(latlonList.get(latlonList.size()-1).get(0))
                .build());
        
        // 대여 정거장과 반납 정거장이 둘 다 존재하는지 확인 필요
        List<LatlonStation> latlonStations = new ArrayList<>();
        latlonStations.add(LatlonStation.builder()
                .type("대여")
                .lat(nearestStationStart.getLat().doubleValue())
                .lon(nearestStationStart.getLng().doubleValue())
                .build());
        latlonStations.add(LatlonStation.builder()
                .type("반납")
                .lat(nearestStationEnd.getLat().doubleValue())
                .lon(nearestStationEnd.getLng().doubleValue())
                .build());

        LatLonRequest response = LatLonRequest.builder()
                .latlonList(LatlonList.builder()
                        .latlon(latlons)
                        .latlonStations(latlonStations)
                        .build())
                .build();

        return response;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        /*
         Description : Haversine 공식을 사용하여 두 좌표 사이의 거리를 미터 단위로 계산
         Params      : 비교 좌표의 위도, 경도 / 정거장의 위도, 경도
         Returns     : 비교 좌표와의 떨어진 거리 (m)
        */

        final int R = 6371; // 지구의 반경(km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // 거리를 미터로 변환

        return distance;
    }

}
