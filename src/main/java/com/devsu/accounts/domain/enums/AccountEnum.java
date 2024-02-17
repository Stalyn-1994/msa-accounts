package com.devsu.accounts.domain.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountEnum {

  SAVINGS("ahorros"), CURRENT("corriente");

  String name;

  public static Optional<AccountEnum> findByCode(String name) {
    return Arrays.stream(AccountEnum.values()).filter(genderEnum -> genderEnum.name().equals(name))
        .findFirst();
  }

}