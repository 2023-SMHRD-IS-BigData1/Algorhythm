package com.smhrd.algo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class WeatherData {
    @Id
    private Long id;
    private String cityName;
    private String temperature;
}
