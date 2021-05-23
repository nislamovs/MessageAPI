package com.consumer.mailingConsumer.controller;


import com.consumer.mailingConsumer.domain.dto.RabbitMessageDto;
import com.consumer.mailingConsumer.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final EmailService emailService;

    @PostMapping("/testemail")
    public ResponseEntity<?> pushMessage(@RequestBody RabbitMessageDto payload) {

        emailService.pushMessage(payload, "asdassd");
        return ResponseEntity.ok("Email message sent!");
    }
}
