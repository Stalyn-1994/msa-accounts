package com.devsu.accounts.helper;


import com.devsu.accounts.service.dto.response.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Helper {

  public static ResponseEntity<BaseResponseDto> buildResponseEntity(Object data,
      HttpStatus httpStatus) {
    return new ResponseEntity<>(BaseResponseDto.builder()
        .code(httpStatus.value())
        .status(httpStatus.getReasonPhrase())
        .data(data)
        .build(), httpStatus);
  }
}