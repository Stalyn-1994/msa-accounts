package com.devsu.accounts.service.mapper;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.MovementsEntity;
import com.devsu.accounts.service.dto.request.MovementRequestDto;

public interface MovementServiceMapper {

  MovementsEntity buildMoveEntity(MovementRequestDto movementRequestDto,
      double balanceTotal, AccountEntity account);

  double calculateBalanceTotal(AccountEntity account);
}
