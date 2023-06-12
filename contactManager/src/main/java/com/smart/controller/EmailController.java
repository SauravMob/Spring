package com.smart.controller;

import com.smart.entities.EmailRequest;
import com.smart.helper.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController{

    @Autowired
    private EmailService emailService;

    @GetMapping("/welcome")
    public String welcome() {
        return "";
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail() {
        boolean result = this.emailService.sendEmail(
                "Just testing subject",
                "Learning to send an email using java. Please tell Saurav that he is successfull in his mission!! Thankyou",
                "shivanigurnale123@gmail.com");
        if (result) {
            return ResponseEntity.ok("Success..");
        } else {
            return ResponseEntity.ok("Failed..");
        }
    }
}