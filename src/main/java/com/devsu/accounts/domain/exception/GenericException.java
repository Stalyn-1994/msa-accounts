package com.devsu.accounts.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class GenericException extends RuntimeException {

  HttpStatus status;
  String message;

}
