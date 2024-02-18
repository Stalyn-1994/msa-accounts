package com.devsu.accounts.service;

import com.devsu.accounts.domain.exception.InternalErrorException;
import com.devsu.accounts.service.dto.request.AccountRequestDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface AccountService {

  @Retryable(retryFor = InternalErrorException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  ResponseEntity<BaseResponseDto> save(AccountRequestDto accountResponseDto);

  @Retryable(retryFor = InternalErrorException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  ResponseEntity<BaseResponseDto> update(AccountRequestDto accountResponseDto);

  @Retryable(retryFor = InternalErrorException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
  ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto, String identification);

  ResponseEntity<BaseResponseDto> delete(String identification);

}
