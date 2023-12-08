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

    public User updateUser(String userId, double addKm) {
        User user = userRepository.findByUserId(userId);
        System.out.println(addKm/1000);
        System.out.println(userId);

        User updateUser = User.builder()
                .userIdx(user.getUserIdx())
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .userNickname(user.getUserNickname())
                .userKm(user.getUserKm() + (addKm/1000))
                .build();

        userRepository.save(updateUser);

        return updateUser;
    }

//    public User updateUser(User user, double addKm) {
//        User updateUser = User.builder()
//                .userIdx(user.getUserIdx())
//                .userId(user.getUserId())
//                .userPw(user.getUserPw())
//                .userNickname(user.getUserNickname())
//                .userKm(user.getUserKm() + addKm)
//                .build();
//
//        userRepository.save(updateUser);
//
//        return updateUser;
//    }

    public boolean idDuplicateCheck(String userId) {
        return userRepository.findByUserId(userId) == null;
    }

    public User loginUser(String userId, String userPw) {
        return userRepository.findByUserIdAndUserPw(userId, userPw);
    }
}
