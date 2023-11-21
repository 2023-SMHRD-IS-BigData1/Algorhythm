package com.smhrd.algo.service;

import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser() {
        User newUser = User.builder()
                .userId("admin")
                .userPw("1234")
                .userName("관리자")
                .userBirthdate(LocalDate.of(1998,1,16))
                .userGender("M")
                .userAddr("월계동")
                .joinedAt(LocalDate.now())
                .build();

        // MemberRepository를 사용하여 User 엔티티를 저장합니다.
        return userRepository.save(newUser);
    }

    public User loginUser(String userId, String userPw) {
        return userRepository.findByUserIdAndUserPw(userId, userPw);
    }

}
