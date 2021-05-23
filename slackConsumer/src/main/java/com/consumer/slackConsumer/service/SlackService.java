package com.consumer.slackConsumer.service;

import com.consumer.slackConsumer.clients.SlackClient;
import com.consumer.slackConsumer.domain.dto.RabbitMessageDto;
import com.consumer.slackConsumer.domain.dto.SlackPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static com.consumer.slackConsumer.configuration.RabbitConfiguration.*;
import static java.lang.Integer.parseInt;
import static java.time.Instant.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackClient slackClient;
    private final MessageConverter messageConverter;
    private final AmqpTemplate template;

    @RabbitListener(bindings = {
            @QueueBinding(
                value = @Queue(value = ROUTE_SLACK_QUEUE, durable = "true"),
                exchange = @Exchange(value = TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                key = ""),
            @QueueBinding(
                value = @Queue(value = ROUTE_SLACK_QUEUE, durable = "true"),
                exchange = @Exchange(value = FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT),
                key = ""),
            @QueueBinding(
                    value = @Queue(value = DELAY_QUEUE_NAME, durable = "true"),
                    exchange = @Exchange(value = DELAYED_EXCHANGE, type = ExchangeTypes.DIRECT, delayed = "true"),
                    key = DELAY_QUEUE_NAME)
    })
    public void pushMessage(Message message,
                            @NotNull(message = "Slack message not set!") RabbitMessageDto payload,
                            @Header(value = NUM_ATTEMPT_HEADER, required = false) String numReattempt,
                            @Header(value = "test", required = false) String testHeader) {

        System.out.println("--------------->>  " + message.getMessageProperties().toString());
        System.out.println("--------------->>  " + message.toString());
        System.out.println("--------------->>  " + message.getMessageProperties().getReceivedDelay());

        Integer delayMs = message.getMessageProperties().getReceivedDelay();

        if (numReattempt != null && parseInt(numReattempt) > 0) {
            slackClient.pushSlackMsg(SlackPayload.builder().text(placeTimestamp() + "Reattempt No -> " + numReattempt + " msg -> " + payload.getMsg()).build());
            template.convertAndSend(DELAYED_EXCHANGE, DELAY_QUEUE_NAME, buildMessage(payload, delayMs, numReattempt));
        } else {
            slackClient.pushSlackMsg(SlackPayload.builder().text(placeTimestamp() + "Last mesg -> " + payload.getMsg()).build());
        }
    }

    private String placeTimestamp() {
        return "Timestamp : [" + now() + "] ";
    }

    private Message buildMessage(Object body, Integer delayMs, String retryCount) {

        MessageProperties props = new MessageProperties();
        Integer retry = parseInt(retryCount);
        retry--;

//        props.setHeader(DELAY_HEADER, delayMs == null ? RETRY_BACKOFF : delayMs);
        props.setDelay(delayMs == null ? parseInt(RETRY_BACKOFF) : delayMs);
        props.setHeader(NUM_ATTEMPT_HEADER, String.valueOf(retry));
        props.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);

        System.out.println("PROPS slack : " + props.toString());

        return messageConverter.toMessage(body, props);
    }
}