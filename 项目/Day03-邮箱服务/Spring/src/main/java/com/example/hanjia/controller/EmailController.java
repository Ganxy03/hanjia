package com.example.hanjia.controller;

import com.example.hanjia.servce.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
public class EmailController {
    private final EmailService emailService;
    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        try {
            emailService.sendVerificationCode(email);
            return ResponseEntity.ok("Send Success.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Send error.");
        }
    }

    @PostMapping("/check-verification-code")
//    public ResponseEntity<String> checkVerificationCode(@RequestBody VerificationCodeRequest request) {
//        String email = request.getEmail();
//        String code = request.getCode();
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email, @RequestParam String code) {
        try (Jedis jedis = new Jedis("124.221.138.245", 6379)) {
            jedis.auth("pwd");
            jedis.select(1);
            String storedCode = jedis.get(email);
            if (storedCode != null && storedCode.equals(code)) {
                return ResponseEntity.ok("Verification success");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Verification error.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("System error.");
        }
    }


}
