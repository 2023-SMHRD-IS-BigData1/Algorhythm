package com.smhrd.algo.controller;

import com.smhrd.algo.model.entity.Bike;
import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.repository.BikeRepository;
import com.smhrd.algo.repository.UserRepository;
import com.smhrd.algo.service.WeatherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final BikeRepository bikeRepository;

    @GetMapping("/test")
    @ResponseBody
    public String weatherData() throws URISyntaxException {
        return new WeatherService().getData();
    }

    @PostConstruct
    public void init() {
        // User 2명 가데이터 저장
        User testUser1 = User.builder()
                .userId("test1")
                .userPw("1234")
                .userName("최윤석")
                .userBirthdate(LocalDate.of(1998,1,16))
                .userGender("M")
                .userAddr("월계동")
                .joinedAt(LocalDate.now())
                .userMileage(0)
                .build();

        userRepository.save(testUser1);

        User testUser2 = User.builder()
                .userId("test2")
                .userPw("1234")
                .userName("최윤희")
                .userBirthdate(LocalDate.of(2000,4,19))
                .userGender("F")
                .userAddr("월계동")
                .joinedAt(LocalDate.now())
                .userMileage(0)
                .build();

        userRepository.save(testUser2);
        
        // 자전거 10대 가데이터 저장
        for(int i=0; i<10; i++) {
            bikeRepository.save(Bike.builder()
                .bikeUse("N")
                .bikeMeter(0)
                .bikeTime(0)
                .build());
        }
        // 정거장 3곳 가데이터 저장
    }
}
