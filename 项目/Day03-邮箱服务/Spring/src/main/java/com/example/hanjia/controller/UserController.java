package com.example.hanjia.controller;

import com.example.hanjia.entity.User;
import com.example.hanjia.servce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> RegisterUser(@RequestBody UserRequest userRequest) {
        User user = userService.RegisterUser(userRequest.getNickname(), userRequest.getPwd());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String nickname, @RequestParam String pwd) {
        boolean loginResult = userService.login(nickname, pwd);
        if(loginResult) {
            User user = userService.getUserByNickname(nickname);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("sorry, u pwd is wrong!");
    }

    public static class UserRequest {
        private String nickname;
        private String pwd;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

    }
}
