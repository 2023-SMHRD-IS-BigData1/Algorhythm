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

    public User createUser(String userId, String userPw, String userName,
                           LocalDate userBirthdate, String userGender,
                           String userAddr) {

        User newUser = User.builder()
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .userBirthdate(userBirthdate)
                .userGender(userGender)
                .userAddr(userAddr)
                .joinedAt(LocalDate.now())
                .build();

        return userRepository.save(newUser);
    }

    public User loginUser(String userId, String userPw) {
        return userRepository.findByUserIdAndUserPw(userId, userPw);
    }

}
