package com.consumer.smsConsumer.service;

import com.consumer.smsConsumer.configuration.RabbitConfiguration;
import com.consumer.smsConsumer.domain.dto.RabbitMessageDto;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.SmsSubmissionResponseMessage;
import com.vonage.client.sms.messages.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class VonageService {

    @Value("${vonage.from}") private String smsFrom;
    @Value("${vonage.to}")   private String smsTo;

    private final VonageClient vonageClient;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = RabbitConfiguration.ROUTE_SMS_QUEUE, durable = "true"),
                    exchange = @Exchange(value = RabbitConfiguration.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = ""),
            @QueueBinding(
                    value = @Queue(value = RabbitConfiguration.ROUTE_SMS_QUEUE, durable = "true"),
                    exchange = @Exchange(value = RabbitConfiguration.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT),
                    key = "")
    })
    public void pushMessage(@NotNull(message = "Slack message not set!") RabbitMessageDto payload) {

        System.out.println(">>>>>>>>>>>>>>>>>              " + payload.getMsg());

        SmsSubmissionResponse responses = vonageClient.getSmsClient().submitMessage(
                new TextMessage(smsFrom, smsTo, payload.getMsg())
        );

        for (SmsSubmissionResponseMessage response : responses.getMessages()) {
            System.out.println(response);
        }
    }
}