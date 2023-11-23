package com.smhrd.algo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Table(name = "user_info")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // 유저 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userIdx")
    private Long userIdx;

    // 유저 아이디
    @Column(name = "user_id", nullable = false, length = 30)
    private String userId;

    // 유저 비밀번호
    @Column(name = "user_pw", nullable = false, length = 30)
    private String userPw;

    // 유저 이름
    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    // 유저 생년월일
    @Column(name = "user_birthdate", nullable = false)
    private LocalDate userBirthdate;

    // 유저 성별
    @Column(name = "user_gender", nullable = false, length = 1)
    private String userGender;

    // 유저 주소
    @Column(name = "user_addr", nullable = false, length = 900)
    private String userAddr;

    // 유저 가입일
    @Column(name = "joined_at", nullable = false)
    private LocalDate joinedAt;

    // 유저 마일리지
    @Column(name = "user_mileage")
    private Integer userMileage;

    // 유저 탄소절감량
    @Column
    private Integer userReduce;

    // 유저 사용거리
    @Column
    private Integer userKm;

    // 유저 칼로리량
    @Column
    private Integer userKcal;

    // 유저 심은나무
    @Column
    private Integer userTree;

    @Builder
    public User(String userId, String userPw, String userName,
                LocalDate userBirthdate, String userGender,
                String userAddr, LocalDate joinedAt, Integer userMileage,
                Integer userReduce, Integer userKm, Integer userKcal, Integer userTree) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userBirthdate = userBirthdate;
        this.userGender = userGender;
        this.userAddr = userAddr;
        this.joinedAt = joinedAt;
        this.userMileage = userMileage;
        this.userReduce = userReduce;
        this.userKm = userKm;
        this.userKcal = userKcal;
        this.userTree = userTree;
    }
}