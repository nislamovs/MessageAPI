package com.consumer.smsConsumer.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RabbitMessageDto {

    private String msg;

    private List<byte[]> fileList;

//    private Instant createdAt;

    private String createdBy;
}
