package com.smhrd.algo.repository;

import com.smhrd.algo.model.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    // Custom query methods if needed
}
