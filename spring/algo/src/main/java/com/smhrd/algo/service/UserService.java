package com.smhrd.algo.service;

import com.smhrd.algo.model.entity.User;
import com.smhrd.algo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String userId, String userPw, String userNickname) {

        User newUser = User.builder()
                .userId(userId)
                .userPw(userPw)
                .userNickname(userNickname)
                .userKm(0)
                .build();

        return userRepository.save(newUser);
    }

    public User updateUser(User user, double addKm) {
        // 기존 User에서 데이터 꺼내서 새로 생성하기
        // 기존 Km에서 add한 뒤에 그 km 곱하기 다른 값

        return null;
    }

    public boolean idDuplicateCheck(String userId) {
        return userRepository.findByUserId(userId) == null;
    }

    public User loginUser(String userId, String userPw) {
        return userRepository.findByUserIdAndUserPw(userId, userPw);
    }
}
