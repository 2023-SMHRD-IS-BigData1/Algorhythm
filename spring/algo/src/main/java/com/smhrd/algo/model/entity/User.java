package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "user_info")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // 유저 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    // 유저 아이디
    @Column(name = "user_id", nullable = false, length = 30)
    private String userId;

    // 유저 비밀번호
    @Column(name = "user_pw", nullable = false, length = 30)
    private String userPw;

    // 유저 이름
    @Column(name = "user_nickname", nullable = false, length = 30)
    private String userNickname;

    // 유저 이름
    @Column(name = "user_gender")
    private String userGender;

    // 유저 이름
    @Column(name = "user_addr")
    private String userAddr;

    // 유저 이름
    @Column(name = "user_age")
    private Integer userAge;

    // 유저 가입일
    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDate joinedAt;

    // 유저 마일리지
    @Column(name = "user_mileage")
    private Integer userMileage;

    // 탄소 절감량
    @Column(name = "user_reduce")
    private double userReduce;

    // 총 km
    @Column(name = "user_km")
    private double userKm;

    // 감소 칼로리
    @Column(name = "user_kcal")
    private double userKcal;

    // 심은 나무 수
    @Column(name = "user_tree")
    private double userTree;

    // 유저 코드
    @Column(name = "user_code")
    private String userCode;

    @Builder
    public User(Long userIdx, String userId, String userPw, String userNickname,
                double userKm, Integer userAge, String userGender,
                String userAddr) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.userPw = userPw;
        this.userNickname = userNickname;
        this.joinedAt = LocalDate.now();
        this.userMileage = (int) (userKm * 100);
        this.userReduce = userKm * 0.211;
        this.userKm = userKm;
        this.userKcal = userKm * 9;
        this.userTree = this.userReduce / 17.2;
        this.userCode = "C";
        this.userAddr = userAddr;
        this.userAge = userAge;
        this.userGender = userGender;
    }
}