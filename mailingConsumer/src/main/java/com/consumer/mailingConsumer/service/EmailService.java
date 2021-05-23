package com.consumer.mailingConsumer.service;

import com.consumer.mailingConsumer.configuration.RabbitConfiguration;
import com.consumer.mailingConsumer.domain.dto.RabbitMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.amqp.core.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage messageTemplate;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = RabbitConfiguration.ROUTE_EMAIL_QUEUE, durable = "true"),
                    exchange = @Exchange(value = RabbitConfiguration.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = ""),
            @QueueBinding(
                    value = @Queue(value = RabbitConfiguration.ROUTE_EMAIL_QUEUE, durable = "true"),
                    exchange = @Exchange(value = RabbitConfiguration.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT),
                    key = "")
    })
    public void pushMessage(@NotNull(message = "Email message not set!") RabbitMessageDto payload,
                            @Header(value = "test", required = false) String header) {

        System.out.println(">>>>>>>>>>>>>>>>>       " + header + "       " + payload.getMsg());

        messageTemplate.setText(payload.getMsg());
        sendMessage(messageTemplate);

    }

    private void sendMessage(SimpleMailMessage message) {
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            log.error("Failed to send email", e);
        }
    }
}