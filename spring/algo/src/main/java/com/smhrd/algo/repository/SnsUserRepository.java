package com.smhrd.algo.repository;

import java.util.Optional;

import com.smhrd.algo.model.entity.SnsUser;
import org.springframework.data.repository.CrudRepository;

public interface SnsUserRepository extends CrudRepository<SnsUser, Long> {

    Optional<SnsUser> findByEmail(String email);
}
