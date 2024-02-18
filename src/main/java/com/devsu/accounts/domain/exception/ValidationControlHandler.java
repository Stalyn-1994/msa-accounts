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

  @ExceptionHandler(value = {InternalErrorException.class})
  protected ResponseEntity<Object> handleConflictInternalServerError(
      InternalErrorException genericException,
      WebRequest request) {
    ErrorDto errorDto = ErrorDto.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(genericException.getMessage())
        .timeStamp(String.valueOf(new Date(System.currentTimeMillis())))
        .resource(((ServletWebRequest) request).getRequest().getRequestURL().toString())
        .build();
    return handleExceptionInternal(genericException, errorDto, new HttpHeaders()
        , HttpStatus.INTERNAL_SERVER_ERROR
        , request);
  }
}