package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "bike_info")
@NoArgsConstructor
public class Bike {

    // 자전거 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_idx")
    private long bikeIdx;

    // 연관관계를 위한 필드
    @OneToMany(mappedBy = "bike")
    private List<Rental> rentalRecord;

    // 자전거 사용여부
    @Column(name = "bike_use", nullable = false, length = 1)
    private String bikeUse;
    
    // 자전거 주행거리
    @Column(name = "bike_meter")
    private Integer bikeMeter;
    
    // 자전거 주행시간
    @Column(name = "bike_time")
    private Integer bikeTime;

    @Builder
    public Bike(String bikeUse, Integer bikeMeter, Integer bikeTime) {
        this.bikeUse = bikeUse;
        this.bikeMeter = bikeMeter;
        this.bikeTime = bikeTime;
    }
}
