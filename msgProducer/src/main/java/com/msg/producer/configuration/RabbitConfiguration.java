package com.msg.producer.configuration;

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


    public static final String ROUTE_EMAIL_QUEUE = "msg.email";
    public static final String ROUTE_SLACK_QUEUE = "msg.slack";
    public static final String ROUTE_SMS_QUEUE = "msg.sms";

    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";


    private final ConnectionFactory connectionFactory;

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(this.connectionFactory);
    }

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

    @Bean
    public Queue emailQueue() {
        return new Queue(ROUTE_EMAIL_QUEUE, true);
    }

    @Bean
    public Queue slackQueue() {
        return new Queue(ROUTE_SLACK_QUEUE, true);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(ROUTE_SMS_QUEUE, true);
    }


    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    //TopicExchange bindings
    @Bean
    public Binding emailTopicBinding() {
        return BindingBuilder.bind(emailQueue()).to(topicExchange()).with(ROUTE_EMAIL_QUEUE);
    }

    @Bean
    public Binding slackTopicBinding() {
        return BindingBuilder.bind(slackQueue()).to(topicExchange()).with(ROUTE_SLACK_QUEUE);
    }

    @Bean
    public Binding smsTopicBinding() {
        return BindingBuilder.bind(smsQueue()).to(topicExchange()).with(ROUTE_SMS_QUEUE);
    }


    //FanoutExchange bindings
    @Bean
    public Binding emailFanoutBinding() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding slackFanoutBinding() {
        return BindingBuilder.bind(slackQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding smsFanoutBinding() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }
}
