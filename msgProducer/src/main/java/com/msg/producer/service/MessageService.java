package com.msg.producer.service;

import com.msg.producer.configuration.RabbitConfiguration;
import com.msg.producer.domain.dto.RabbitMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final AmqpTemplate template;
    private final MessageConverter messageConverter;

    public void pushToEmail(RabbitMessageDto msgBody) {

        log.info("Push to email queue");
        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_EMAIL_QUEUE, buildMessage(msgBody));
    }

    public void pushToSlack(RabbitMessageDto msgBody) {

        log.info("Push to slack queue");
        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_SLACK_QUEUE, buildMessage(msgBody));
    }

    public void pushToSms(RabbitMessageDto msgBody) {

        log.info("Push to sms gateway queue");
        template.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE, RabbitConfiguration.ROUTE_SMS_QUEUE, buildMessage(msgBody));
    }

    public void pushToAll(RabbitMessageDto msgBody) {

        log.info("Push to all queues");
        template.convertAndSend(RabbitConfiguration.FANOUT_EXCHANGE, "", buildMessage(msgBody));
    }

    public void pushDelayed(RabbitMessageDto msgBody, Integer delayMs, Integer retryCount) {

        System.out.println("DelayMs : [" + delayMs + "] ms");
        System.out.println("Retry count : [" + retryCount + "] times");
        log.info("Push delayed to slack");
        template.convertAndSend(RabbitConfiguration.DELAYED_EXCHANGE, RabbitConfiguration.DELAY_QUEUE_NAME, buildMessage(msgBody, delayMs, retryCount));
    }

    private Message buildMessage(Object body, Integer delayMs, Integer retryCount) {

        MessageProperties props = new MessageProperties();
//        props.setContentType("application/json");
        props.setHeader("test", "123");
        if (retryCount != null) {
            props.setHeader(RabbitConfiguration.NUM_ATTEMPT_HEADER, String.valueOf(retryCount));   //String.valueOf(retryCount)
        }
//        if (delayMs != null) {
//            props.setHeader(RabbitConfiguration.DELAY_HEADER, String.valueOf(delayMs));    /// String.valueOf(delayMs)
//        }
        props.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        props.setDelay(delayMs);
        System.out.println("Props: >>> " + props.toString());

        return messageConverter.toMessage(body, props);
    }

    private Message buildMessage(Object body) {

        return buildMessage(body, null, null);
    }
}
