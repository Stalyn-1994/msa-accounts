package com.devsu.accounts.domain.exception;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationControlHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    ErrorDto errorDto = ErrorDto.builder()
        .message(ex.getMessage())
        .timeStamp(String.valueOf(new Date(System.currentTimeMillis())))
        .resource(((ServletWebRequest) request).getRequest().getRequestURL().toString())
        .build();
    return handleExceptionInternal(ex, errorDto, new HttpHeaders(), HttpStatus.NOT_FOUND,
        request);
  }

  @ExceptionHandler(value = {GenericException.class})
  protected ResponseEntity<Object> handleConflictGenericException(GenericException genericException,
      WebRequest request) {
    ErrorDto errorDto = ErrorDto.builder()
        .code(genericException.status.value())
        .message(genericException.getMessage())
        .timeStamp(String.valueOf(new Date(System.currentTimeMillis())))
        .resource(((ServletWebRequest) request).getRequest().getRequestURL().toString())
        .build();
    return handleExceptionInternal(genericException, errorDto, new HttpHeaders(),
        genericException.getStatus(),
        request);
  }
}