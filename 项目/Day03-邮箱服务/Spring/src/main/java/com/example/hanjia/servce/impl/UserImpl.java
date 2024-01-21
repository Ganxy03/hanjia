package com.example.hanjia.servce.impl;

import com.example.hanjia.entity.User;
import com.example.hanjia.repository.UserRepository;
import com.example.hanjia.servce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.EnumMap;

@Service
public class UserImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User RegisterUser(String nickname, String pwd) {
        User user = new User();
        user.setNickname(nickname);
        user.setPwd(pwd);
        user.setCreate_time(LocalDateTime.now());
        return userRepository.save(user);
    }


    @Override
    public boolean login(String nickname, String pwd) {
        User user = userRepository.findByNickname(nickname);
        if(user != null && user.getPwd().equals(pwd)) {
            return true;
        }
        return false;
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

}
