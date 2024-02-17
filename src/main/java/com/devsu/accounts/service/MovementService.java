package com.devsu.accounts.service;

import com.devsu.accounts.service.dto.request.MovementRequestDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface MovementService {


  ResponseEntity<BaseResponseDto> save(MovementRequestDto movementRequestDto);

  ResponseEntity<BaseResponseDto> get(LocalDate initDate, LocalDate endDate, String customer);

  ResponseEntity<BaseResponseDto> update(MovementRequestDto movementRequestDto);

  ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto, Long identification);

  ResponseEntity<BaseResponseDto> delete(Long identification);

}
