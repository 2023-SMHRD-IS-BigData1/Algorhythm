package com.smhrd.algo.controller;

import com.smhrd.algo.model.dto.NaviPersonResponse;
import com.smhrd.algo.model.dto.PoiResponse;
import com.smhrd.algo.service.TmapService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {
    @GetMapping("/poisearch")
    @ResponseBody
    public PoiResponse poiSearch(@RequestParam String searchKeyword) {
        TmapService ts = new TmapService();
        String json = ts.poiSearch(searchKeyword);
        return ts.convertToObject(json);
    }

    @GetMapping("/naviperson")
    @ResponseBody
    public NaviPersonResponse naviSearch() {
        TmapService ts = new TmapService();
        String json =ts.naviPerson();
        return ts.converToNaviObject(json);
    }

//    @GetMapping("/naviperson")
//    @ResponseBody
//    public String naviSearch() {
//        TmapService ts = new TmapService();
//        return ts.naviPerson();
//    }
}
