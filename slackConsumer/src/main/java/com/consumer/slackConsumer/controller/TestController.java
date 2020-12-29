package com.consumer.slackConsumer.controller;


import com.consumer.slackConsumer.domain.dto.RabbitMessageDto;
import com.consumer.slackConsumer.service.SlackService;
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

    private final SlackService slackService;

    @PostMapping("/testslack")
    public ResponseEntity<?> pushMessage(@RequestBody RabbitMessageDto payload) {

        slackService.pushMessage(payload);
        return ResponseEntity.ok("Message sent!");
    }
}
