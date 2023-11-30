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

    public User createUser(String userId, String userPw, String userNickname) {

        User newUser = User.builder()
                .userId(userId)
                .userPw(userPw)
                .userNickname(userNickname)
                .userMileage(0)
                .userReduce(0)
                .userKm(0)
                .userKcal(0)
                .userTree(0)
                .build();

        return userRepository.save(newUser);
    }

    public User loginUser(String userId, String userPw) {
        return userRepository.findByUserIdAndUserPw(userId, userPw);
    }

}
