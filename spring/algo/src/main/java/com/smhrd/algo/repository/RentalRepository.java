package com.smhrd.algo.repository;

import com.smhrd.algo.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {

}
