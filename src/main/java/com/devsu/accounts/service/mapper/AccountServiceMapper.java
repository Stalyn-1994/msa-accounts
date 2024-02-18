package com.devsu.accounts.service.mapper;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.MovementsEntity;
import com.devsu.accounts.service.dto.request.AccountRequestDto;
import com.devsu.accounts.service.dto.response.AccountResponseDto;
import com.devsu.accounts.service.dto.response.MovementResponseDto;
import java.util.Random;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class AccountServiceMapper {

  @Mapping(target = "accountNumber", expression = "java(generateAccountNumber())")
  @Mapping(target = "customer", source = "accountRequestDto.name")
  public abstract AccountEntity toAccountEntity(AccountRequestDto accountRequestDto);

  public abstract AccountResponseDto toAccountResponseDto(AccountEntity accountEntity);

  @Mapping(target = "accountNumber", source = "movementsEntity.accountNumber.accountNumber")
  @Mapping(target = "customer", source = "movementsEntity.accountNumber.customer")
  @Mapping(target = "initialBalance", source = "movementsEntity.accountNumber.initialBalance")
  @Mapping(target = "status", source = "movementsEntity.accountNumber.status")
  @Mapping(target = "type", source = "movementsEntity.accountNumber.type")
  public abstract MovementResponseDto toAccountResponseDto(MovementsEntity movementsEntity);

  public String generateAccountNumber() {
    Random random = new Random();
    return String.valueOf(random.nextLong() & Long.MAX_VALUE);
  }
}

