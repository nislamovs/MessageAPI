package com.consumer.slackConsumer.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class RabbitConfiguration {

    public static final String DELAY_QUEUE_NAME = "delayed.queue";
    public static final String ROUTE_SLACK_QUEUE = "msg.slack";

    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String DELAYED_EXCHANGE = "delayedExchange";

    public static final String DELAY_HEADER = "x-delay";
    public static final String NUM_ATTEMPT_HEADER = "x-num-attempt";
    public static final String RETRY_BACKOFF = "5000";      //ms
    public static final Integer RETRY_COUNT = 5;


    private final ConnectionFactory connectionFactory;

//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(this.connectionFactory);
//    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate template = new RabbitTemplate(this.connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory);
        factory.setMessageConverter(messageConverter());
//        factory.setConcurrentConsumers(3);
//        factory.setMaxConcurrentConsumers(10);
        return factory;
    }
}
