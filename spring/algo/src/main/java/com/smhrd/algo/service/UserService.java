package com.smhrd.algo.service;

import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;

    public User createUser() {
        User newUser = User.builder()
                .userId("admin")
                .userPW("1234")
                .userName("관리자")
                .userBirthdate(LocalDate.of(1998,1,16))
                .userGender("M")
                .userAddr("월계동")
                .joinedAt(LocalDate.now())
                .build();

        // MemberRepository를 사용하여 User 엔티티를 저장합니다.
        return memberRepository.save(newUser);
    }

}
