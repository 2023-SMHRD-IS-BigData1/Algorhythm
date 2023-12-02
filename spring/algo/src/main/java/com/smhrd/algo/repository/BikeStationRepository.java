package com.smhrd.algo.repository;

import com.smhrd.algo.model.entity.BikeStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BikeStationRepository extends JpaRepository<BikeStation, Long> {

}
