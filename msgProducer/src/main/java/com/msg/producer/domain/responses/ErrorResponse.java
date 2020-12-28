package com.msg.producer.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.String.format;

@Data
@AllArgsConstructor
public class ErrorResponse {

    public String errorText;

    public ErrorResponse(String format, Object... args) {
        this(format(format, args));
    }
}


