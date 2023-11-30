package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.LatLonRequest;
import com.smhrd.algo.model.dto.NaviPersonResponse;
import com.smhrd.algo.model.dto.NaviTransportResponse;
import com.smhrd.algo.model.dto.PoiResponse;
import com.smhrd.algo.model.entity.BikeStation;
import com.smhrd.algo.repository.BikeStationRepository;
import com.smhrd.algo.service.TmapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ApiController {

    private final BikeStationRepository bikeStationRepository;
    private final TmapService ts;

    @GetMapping("/poisearch")
    @ResponseBody
    public PoiResponse poiSearch(@RequestParam String searchKeyword) {
        String json = ts.poiSearch(searchKeyword);
        return ts.convertToObject(json);
    }

    @GetMapping("/getstation")
    @ResponseBody
    public List<BikeStation> getStation() {
        return bikeStationRepository.findAll();
    }

    @GetMapping("/navitransport")
    @ResponseBody
    public NaviTransportResponse getTransportRoute() {
        String json = ts.naviTransport(null);
        NaviTransportResponse object = ts.convertToTransportObject(json);
        return ts.setLatLon(object);
    }

    @PostMapping("/naviperson")
    @ResponseBody
    public NaviPersonResponse naviSearch(@RequestBody LatLonRequest latlon) {

        String json =ts.naviPerson(latlon);

        LatLonRequest findStation = ts.findStation(ts.convertToPersonObject(json));

        json = ts.naviPerson(findStation);

        return ts.convertToPersonObject(json);
    }

//    @GetMapping("/naviperson")
//    @ResponseBody
//    public String naviSearch() {
//        return ts.naviPerson();
//    }
}
