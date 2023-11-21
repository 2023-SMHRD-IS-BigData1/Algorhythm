package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "rental_info")
@NoArgsConstructor
public class Rental {

    // 대여 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_idx")
    private long rentIdx;

    // 자전거 순번
    @ManyToOne
    @JoinColumn(name = "bike_idx")
    private Bike bike;

    // 정류장 순번
    @ManyToOne
    @JoinColumn(name = "station_idx")
    private BikeStation station;

    // 유저 순번
    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    // 대여정류장 순번
    @Column(name = "rental_station_idx")
    private long rentalStationIdx;

    // 대여 시간
    @Column(name = "rented_at")
    private LocalDateTime rentedAt;

    // 반납 정류장 순번
    @Column(name = "return_station_idx", nullable = false)
    private Integer returnStationIdx;

    // 반납 시간
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    // 대여 상태
    @Column(name = "rental-status")
    private String rentalStatus;

    @Builder
    public Rental(Bike bike, BikeStation station, User user,
                  long rentalStationIdx, LocalDateTime rentedAt,
                  Integer returnStationIdx, LocalDateTime returnedAt,
                  String rentalStatus) {
        this.bike = bike;
        this.station = station;
        this.user = user;
        this.rentalStationIdx = rentalStationIdx;
        this.rentedAt = rentedAt;
        this.returnStationIdx = returnStationIdx;
        this.returnedAt = returnedAt;
        this.rentalStatus = rentalStatus;
    }
}
