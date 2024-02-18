package com.devsu.accounts.service;

import com.devsu.accounts.domain.exception.GenericException;
import com.devsu.accounts.service.dto.request.AccountRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaReceiver {

  final AccountService accountService;
  final ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "example", groupId = "my-group")
  public void listen(String message) {
    try {
      AccountRequestDto accountRequestDto = objectMapper.readValue(message,
          AccountRequestDto.class);
      accountService.save(accountRequestDto);
    } catch (Exception exception) {
      throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in decode request");
    }
  }
}