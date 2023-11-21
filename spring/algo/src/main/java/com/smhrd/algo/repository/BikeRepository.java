package com.smhrd.algo.repository;

import com.smhrd.algo.model.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {

}
