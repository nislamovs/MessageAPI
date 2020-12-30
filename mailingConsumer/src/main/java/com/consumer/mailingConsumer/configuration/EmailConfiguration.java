package com.consumer.mailingConsumer.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;


@Configuration
public class EmailConfiguration {

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.to}")
    private String emailTo;

    @Value("${email.reply.to}")
    private String replyTo;

    @Value("${email.subject.prefix}")
    private String subject;


    @Bean
    public SimpleMailMessage messageTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setReplyTo(replyTo);
        message.setTo(emailTo);
        message.setSubject(subject);

        return message;
    }
}