package com.consumer.slackConsumer.clients;

import com.consumer.slackConsumer.configuration.FeignConfiguration;
import com.consumer.slackConsumer.domain.dto.SlackPayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "slackClient1", url = "${slack.url}", configuration = FeignConfiguration.class)
public interface SlackClient {

    @PostMapping
    void pushSlackMsg(@RequestBody SlackPayload payload);
}