package com.msg.producer.controller;


import com.msg.producer.domain.dto.RabbitMessageDto;
import com.msg.producer.domain.exceptions.UnknownChannelException;
import com.msg.producer.domain.responses.DefaultResponse;
import com.msg.producer.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

import static java.lang.String.format;
import static java.time.Instant.now;
import static org.springframework.http.ResponseEntity.ok;

@RequestMapping(value = "/api/v1/message")
@RequiredArgsConstructor
@Slf4j
@RestController
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{messagingChannel}")
    public ResponseEntity<?> pushMsgToChannel(@PathVariable(value = "messagingChannel") @NotBlank(message = "Message channel cannot be empty!") String msgChannel,
                                              @RequestBody RabbitMessageDto msgBody) {

        log.info("Messaging channel: " + msgChannel);

        switch (msgChannel.toUpperCase()) {

            case "EMAIL":
                messageService.pushToEmail(msgBody);
                break;
            case "SLACK":
                messageService.pushToSlack(msgBody);
                break;
            case "SMS":
                messageService.pushToSms(msgBody);
                break;
            case "ALL":
                messageService.pushToAll(msgBody);
                break;

            default:
                throw new UnknownChannelException(format("Channel with name [%s] not found!", msgChannel));
        }

        return ok(DefaultResponse.builder().status("Message pushed!").timestamp(now()).build());
    }
}
