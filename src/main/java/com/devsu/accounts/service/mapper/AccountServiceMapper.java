package com.devsu.accounts.service.mapper;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.service.dto.request.AccountRequestDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public abstract class AccountServiceMapper {

  public abstract AccountEntity toAccountEntity(AccountRequestDto accountEntity);
}