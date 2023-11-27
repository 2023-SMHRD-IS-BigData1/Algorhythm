package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.LatLonRequest;
import com.smhrd.algo.model.dto.NaviPersonResponse;
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

    @GetMapping("/poisearch")
    @ResponseBody
    public PoiResponse poiSearch(@RequestParam String searchKeyword) {
        TmapService ts = new TmapService();
        String json = ts.poiSearch(searchKeyword);
        return ts.convertToObject(json);
    }

    @GetMapping("/getstation")
    @ResponseBody
    public List<BikeStation> getStation() {
        return bikeStationRepository.findAll();
    }

    @PostMapping("/naviperson")
    @ResponseBody
    public NaviPersonResponse naviSearch(@RequestBody LatLonRequest latlon) {

        TmapService ts = new TmapService();
        String json =ts.naviPerson(latlon);

        ts.findStation(ts.converToNaviObject(json));

        return ts.converToNaviObject(json);
    }

//    @GetMapping("/naviperson")
//    @ResponseBody
//    public String naviSearch() {
//        TmapService ts = new TmapService();
//        return ts.naviPerson();
//    }
}
