package com.example.hanjia.repository;

import com.example.hanjia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByNickname(String nickname);
}
