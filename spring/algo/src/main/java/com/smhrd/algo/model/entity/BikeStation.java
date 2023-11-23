package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Table(name = "bike_station_info")
@NoArgsConstructor
public class BikeStation {

    // 정류장 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_idx")
    private long stationIdx;

    //일대다 정류장별 보유자전거 관계
    @OneToMany(mappedBy = "bikestation")
    private List<StationBike> haveRecord;
    // 정류장 이름
    @Column(name = "station_name", nullable = false, length = 80)
    private String stationName;
   
    // 정류장 주소
    @Column(name = "station_addr", nullable = false, length = 800)
    private String stationAddr;
    
    // 정류장 위도
    @Column(name = "lat", precision = 17, scale = 14)
    private BigDecimal lat;
   
    // 정류장 경도
    @Column(name = "lng", precision = 17, scale = 14)
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
