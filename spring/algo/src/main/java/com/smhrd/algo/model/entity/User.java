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
public class User {
    /*        User newUser = User.builder()
                               .userName(String)
                               .userPW(String)
                               .userBirthdate(LocalDate)
                               .userGender(String)
                               .userAddr(String)
                               .joinedAt(LocalDate.now()) // 현재 시간으로 설정
                               .build();
    */
    // 유저 순번
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userIdx")
    private Long userIdx;

    // 연관관계를 위한 필드
    @OneToMany(mappedBy = "user")
    private List<Rental> rentalRecord;

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
    private Integer userMileage = 0;

    @Builder
    public User(Long userIdx, String userId, String userPw,
                String userName, LocalDate userBirthdate, String userGender,
                String userAddr, LocalDate joinedAt, Integer userMileage) {
        this.userIdx = userIdx;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userBirthdate = userBirthdate;
        this.userGender = userGender;
        this.userAddr = userAddr;
        this.joinedAt = joinedAt;
        this.userMileage = userMileage;
    }
}