package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "weather_info")
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfo {
    // 날씨 순번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_idx")
    private Long weatherIdx;

    // 날씨 온도
    @Column(name = "weather_temp")
    private double weatherTemp;

    // 날씨 온도
    @Column(name = "weather_rain")
    private double weatherRain;

    // 기록 시간
    @Column(name = "weather_time")
    private LocalDateTime weatherTime;
}
