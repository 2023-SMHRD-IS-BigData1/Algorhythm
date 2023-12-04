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

    // 유저 가입일
    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDate joinedAt;

    // 유저 마일리지
    @Column(name = "user_mileage")
    private Integer userMileage;

    // 유저 마일리지
    @Column(name = "user_reduce")
    private Integer userReduce;

    // 유저 마일리지
    @Column(name = "user_km")
    private Integer userKm;

    // 유저 마일리지
    @Column(name = "user_kcal")
    private Integer userKcal;

    // 유저 마일리지
    @Column(name = "user_tree")
    private Integer userTree;

    @Builder
    public User(String userId, String userPw, String userNickname,
                Integer userMileage, Integer userReduce,
                Integer userKm, Integer userKcal,
                Integer userTree) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNickname = userNickname;
        this.joinedAt = LocalDate.now();
        this.userMileage = userMileage;
        this.userReduce = userReduce;
        this.userKm = userKm;
        this.userKcal = userKcal;
        this.userTree = userTree;
    }
}