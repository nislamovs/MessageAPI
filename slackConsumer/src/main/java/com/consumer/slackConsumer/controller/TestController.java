package com.consumer.slackConsumer.controller;


import com.consumer.slackConsumer.domain.dto.RabbitMessageDto;
import com.consumer.slackConsumer.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final SlackService slackService;
    private final MessageConverter messageConverter;

    @PostMapping("/testslack")
    public ResponseEntity<?> pushMessage(@RequestBody RabbitMessageDto payload) {

//        slackService.pushMessage(payload, "testHeader1", "testHeader2", "testHeader3");
        return ResponseEntity.ok("Message sent!");
    }

//    private Message buildMessage(Object body) {
//
//        MessageProperties props = new MessageProperties();
//        props.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
//
//        return messageConverter.toMessage(body, props);
//    }
}
