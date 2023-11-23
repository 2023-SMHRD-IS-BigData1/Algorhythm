package com.smhrd.algo.controller;

import com.smhrd.algo.service.TmapService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {
    @GetMapping("/poisearch")
    @ResponseBody
    public String poiSearch(@RequestParam String searchKeyword) {
        return new TmapService().poiSearch(searchKeyword);
    }
}
