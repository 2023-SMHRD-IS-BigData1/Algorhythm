package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "rental_station_info")
@NoArgsConstructor
public class BikeStation {

    // 정류장 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_station_idx")
    private long stationIdx;

    // 정류장 이름
    @Column(name = "rental_station_name", nullable = false, length = 200)
    private String stationName;
   
    // 정류장 주소
    @Column(name = "rental_station_detail", nullable = false, length = 300)
    private String stationAddr;
    
    // 정류장 위도
    @Column(name = "rental_station_lat", precision = 17, scale = 14)
    private BigDecimal lat;
   
    // 정류장 경도
    @Column(name = "rental_station_lng", precision = 17, scale = 14)
    private BigDecimal lng;

    @Builder
    public BikeStation(String stationName, String stationAddr,
                       BigDecimal lat, BigDecimal lng) {
        this.stationName = stationName;
        this.stationAddr = stationAddr;
        this.lat = lat;
        this.lng = lng;
    }
}
