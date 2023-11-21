package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "station_bike")
@NoArgsConstructor
@AllArgsConstructor @Builder
public class StationBike {
    /*        StationBike newStationBike =StationBike.builder()
                                                    .haveAt(LocalDateTime)

    */
    // 보유 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "have_idx")
    private long haveIdx;

    // 정류장 순번
    @Column(name = "station_idx")
    private long stationIdx;

    // 자전거 순번
    @Column(name = "bike_idx")
    private long bikeIdx;

    // 처리 시간
    @Column(name = "have_at")
    private LocalDateTime haveAt;
}
