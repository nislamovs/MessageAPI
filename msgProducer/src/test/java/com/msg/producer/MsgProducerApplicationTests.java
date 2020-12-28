package com.msg.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msg.producer.domain.dto.RabbitMessageDto;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static java.time.Instant.now;

@SpringBootTest
class MsgProducerApplicationTests {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void contextLoads() {
	}

	@Test
	public void test1() {
        System.out.println(now());
    }

	@Test
	public void test2() throws JsonProcessingException {
		RabbitMessageDto message = RabbitMessageDto.builder()
				.msg("brijertgeoty")
				.createdAt(now())
				.createdBy("Admin")
				.fileList(
						Arrays.asList(
								"111111111111111111111111111111111".getBytes(),
								"000000000000000000000000000000000".getBytes()
						)
				).build();


		String objectAsString = objectMapper.writeValueAsString(message);

		System.out.println(">>>  " + objectAsString);
	}

}
