package com.example.hanjia.servce;

import com.example.hanjia.entity.User;

public interface UserService {
    User RegisterUser(String nickname, String pwd);

    boolean login(String nickname, String password);

    User getUserByNickname(String nickname);
}
