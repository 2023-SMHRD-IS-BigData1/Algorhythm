package com.smhrd.algo.controller;

import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.repository.UserRepository;
import com.smhrd.algo.service.WeatherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;

    @GetMapping("/test")
    @ResponseBody
    public String weatherData() {
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
        


    }
}
