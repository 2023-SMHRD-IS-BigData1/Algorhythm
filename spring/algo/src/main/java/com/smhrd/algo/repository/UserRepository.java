package com.smhrd.algo.repository;

import com.smhrd.algo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserIdAndUserPw(String userId, String userPw);
}
