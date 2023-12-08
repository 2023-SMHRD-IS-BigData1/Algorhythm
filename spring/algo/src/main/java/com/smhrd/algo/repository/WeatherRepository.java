package com.smhrd.algo.repository;

import com.smhrd.algo.model.entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {

}
