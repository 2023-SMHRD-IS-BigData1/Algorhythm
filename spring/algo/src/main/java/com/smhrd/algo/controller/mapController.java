package com.smhrd.algo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mapController {
    @GetMapping("/tmap")
    public String tMapTest() {

        return "mapsearch";
    }

    @GetMapping("/kakaomap")
    public String kakaoMapTest() {

        return "kakaoMapTest";
    }
}
