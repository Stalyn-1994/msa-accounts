package com.devsu.accounts.helper.factory;

import com.devsu.accounts.domain.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountWithoutMovements implements AccountInterface {

  @Override
  public Double calculateInitialBalance(AccountEntity account) {
    return account.getInitialBalance();
  }
}