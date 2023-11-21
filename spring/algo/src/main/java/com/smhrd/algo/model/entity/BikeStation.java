package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Getter
@Table(name = "bike_station_info")
@NoArgsConstructor
@AllArgsConstructor @Builder
public class BikeStation {
    /*        BikeStation newStation =BikeStation.builder()
                                                .stationName(String)
                                                .stationAddr1(String)
                                                .stationAddr2(String)
                                                .lat(BigDecimal)
                                                .lng(BigDecimal)
     */
    // 정류장 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_idx")
    private long stationIdx;
    
    // 정류장 이름
    @Column(name = "station_name")
    private String stationName;
   
    // 정류장 주소
    @Column(name = "station_addr1")
    private String stationAddr1;
    
    // 정류장 상세주소
    @Column(name = "station_addr2")
    private String stationAddr2;
    
    // 정류장 위도
    @Column(name = "lat")
    private BigDecimal lat;
   
    // 정류장 경도
    @Column(name = "lng")
    private BigDecimal lng;
}
