package com.devsu.accounts.domain.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InternalErrorException extends RuntimeException {

  String message;
}
