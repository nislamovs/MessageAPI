package com.consumer.smsConsumer.controller;


import com.consumer.smsConsumer.domain.dto.RabbitMessageDto;
import com.consumer.smsConsumer.service.VonageService;
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

    private final VonageService vonageService;

    @PostMapping("/testsms")
    public ResponseEntity<?> pushMessage(@RequestBody RabbitMessageDto payload) {

        vonageService.pushMessage(payload);
        return ResponseEntity.ok("Sms message sent!");
    }
}
