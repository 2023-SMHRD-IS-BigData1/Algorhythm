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
@AllArgsConstructor @Builder
public class Rental {
    /*        Rental newRental = Rental.builder()
                                    .rentalStationIdx
                                    .rentedAt(LocalDateTime)
                                    .returnStationIdx(Integer)
                                    .returnedAt(LocalDateTime)
                                    .rentalStatus(String)

    */
    // 대여 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_idx")
    private long rentIdx;

    // 자전거 순번
    @Column(name = "bike_idx")
    private long bikeIdx;

    // 유저 순번
    @Column(name = "user_idx")
    private long userIdx;

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
}
