package com.smhrd.algo.controller.dashboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/dashboard")
public class DashBoardController {
    @GetMapping()
    public String dashBoard() {return "dashboard";}

    @GetMapping("/map")
    public String dashBoardMap() {return "dashboard_map";}

    @GetMapping("/month-chart")
    public String dashBoardMonthChart() {return "day_charts";}

    @GetMapping("/station-chart")
    public String dashBoardStationChart() {return "Station_charts";}
}
