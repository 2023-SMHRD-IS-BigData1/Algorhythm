package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "pop_info")
@NoArgsConstructor
@AllArgsConstructor @Builder
public class Population {
    /*        Population newPopulation = Population.builder()
                                                .pop_num(Integer)
                                                .popDong(String)
                                                .popAge(Integer)
                                                .popGender(String)
    */
    // 인구 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pop_idx")
    private long popIdx;

    // 인구 수
    @Column(name = "pop_num")
    private Integer popNum;

    // 인구 거주 동
    @Column(name = "pop_dong", nullable = false, length = 200)
    private String popDong;

    // 인구 나이
    @Column(name= "pop_age")
    private  Integer popAge;

    // 인구 성별
    @Column(name = "pop_gender", nullable = false, length = 1)
    private String popGender;
}
