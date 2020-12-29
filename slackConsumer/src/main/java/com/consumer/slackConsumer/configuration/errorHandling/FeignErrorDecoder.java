package com.consumer.slackConsumer.configuration.errorHandling;

import com.consumer.slackConsumer.domain.exceptions.SlackTokenAuthException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (    response.status() == HttpStatus.FORBIDDEN.value() || response.status() == HttpStatus.NOT_FOUND.value())
            throw new SlackTokenAuthException("Some problem was encountered using slack toke. Check if it sis correct and try again.");
        return new Exception(response.reason());
    }
}