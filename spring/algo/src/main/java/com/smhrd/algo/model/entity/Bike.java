package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "bike_info")
@NoArgsConstructor
@AllArgsConstructor @Builder
public class Bike {
    /*        Bike newBike = Bike.builder()
                                .bikeUse(String)
                                .bikeMeter(Integer)
                                .bikeTime(Integer)
    */
    // 자전거 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_idx")
    private long bikeIdx;

    // 자전거 사용여부
    @Column(name = "bike_use", nullable = false, length = 1)
    private String bikeUse;
    
    // 자전거 주행거리
    @Column(name = "bike_meter")
    private Integer bikeMeter;
    
    // 자전거 주행시간
    @Column(name = "bike_time")
    private Integer bikeTime;
}
