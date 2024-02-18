package com.devsu.accounts.service.mapper;

import static com.devsu.accounts.util.Constants.INSUFFICIENT_BALANCE;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.MovementsEntity;
import com.devsu.accounts.domain.exception.GenericException;
import com.devsu.accounts.helper.factory.AccountInterface;
import com.devsu.accounts.helper.factory.AccountMovementsEnum;
import com.devsu.accounts.service.dto.request.MovementRequestDto;
import jakarta.annotation.Resource;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovementServiceMapperImpl implements MovementServiceMapper {

  @Resource
  final Map<AccountMovementsEnum, AccountInterface> loadServices;

  @Override
  public MovementsEntity buildMoveEntity(MovementRequestDto movementRequestDto,
      double balanceTotal, AccountEntity account) {
    if (balanceTotal < 0 || (balanceTotal + movementRequestDto.getAmount()) < 0) {
      throw new GenericException(HttpStatus.FORBIDDEN, INSUFFICIENT_BALANCE);
    }
    return MovementsEntity.builder()
        .date(new Date(System.currentTimeMillis()))
        .amount(movementRequestDto.getAmount())
        .balance(balanceTotal + movementRequestDto.getAmount())
        .accountNumber(account)
        .type(buildTypeMovement(movementRequestDto.getAmount())).build();

  }

  private String buildTypeMovement(double amount) {
    StringBuilder typeMovement = new StringBuilder();
    if (amount > 0) {
      typeMovement.append("Deposit of ");
    } else {
      typeMovement.append("Withdrawal of ");
    }
    typeMovement.append(amount);
    return typeMovement.toString();
  }

  public double calculateBalanceTotal(AccountEntity account) {
    if (!account.getMovements().isEmpty()) {
      return loadServices.get(AccountMovementsEnum.WITH_MOVEMENTS)
          .calculateInitialBalance(account);
    }
    return loadServices.get(AccountMovementsEnum.WITHOUT_MOVEMENTS)
        .calculateInitialBalance(account);
  }
}
