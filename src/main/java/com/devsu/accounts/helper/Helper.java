package com.devsu.accounts.helper;


import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;

public class Helper {

  public static ResponseEntity<BaseResponseDto> buildResponseDto(Object data,
      HttpStatus httpStatus) {
    return new ResponseEntity<>(BaseResponseDto.builder()
        .code(httpStatus.value())
        .status(httpStatus.getReasonPhrase())
        .data(data)
        .build(), httpStatus);
  }
}