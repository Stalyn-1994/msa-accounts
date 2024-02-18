package com.devsu.accounts.helper.factory;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FactoryConfiguration {

  final AccountWithMovements accountWithMovements;
  final AccountWithoutMovements accountWithoutMovements;

  @Bean
  public Map<AccountMovementsEnum, AccountInterface> loadServices() {
    Map<AccountMovementsEnum, AccountInterface> map = new LinkedHashMap<>();
    map.put(AccountMovementsEnum.WITHOUT_MOVEMENTS, accountWithoutMovements);
    map.put(AccountMovementsEnum.WITH_MOVEMENTS, accountWithMovements);
    return map;
  }
}