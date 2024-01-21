package com.example.hanjia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class halo {
    @GetMapping("/halo")
    public String halo() {
        return "halo";
    }
}
