package com.consumer.smsConsumer.configuration;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.messages.TextMessage;
import feign.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class VonageConfiguration {

    @Value("${vonage.apiKey}")
    private String apiKey;

    @Value("${vonage.apiSecret}")
    private String apiSecret;

    @Bean
    public VonageClient vonageClient() {
        return VonageClient.builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
    }
}