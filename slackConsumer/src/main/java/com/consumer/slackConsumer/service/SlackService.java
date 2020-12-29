package com.consumer.slackConsumer.service;

import com.consumer.slackConsumer.clients.SlackClient;
import com.consumer.slackConsumer.configuration.RabbitConfiguration;
import com.consumer.slackConsumer.domain.dto.RabbitMessageDto;
import com.consumer.slackConsumer.domain.dto.SlackPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackClient slackClient;

    @RabbitListener(bindings = {
            @QueueBinding(
                value = @Queue(value = RabbitConfiguration.ROUTE_SLACK_QUEUE, durable = "true"),
                exchange = @Exchange(value = RabbitConfiguration.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                key = ""),
            @QueueBinding(
                value = @Queue(value = RabbitConfiguration.ROUTE_SLACK_QUEUE, durable = "true"),
                exchange = @Exchange(value = RabbitConfiguration.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT),
                key = "")
    })
    public void pushMessage(@NotNull(message = "Slack message not set!") RabbitMessageDto payload) {

        System.out.println(">>>>>>>>>>>>>>>>>              " + payload.getMsg());

        slackClient.pushSlackMsg(SlackPayload.builder().text(payload.getMsg()).build());

//        return "Done!";
    }
}