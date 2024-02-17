package com.devsu.accounts.repository;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.MovementsEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends CrudRepository<MovementsEntity, Long> {

  MovementsEntity findMovementsEntitiesByAccountNumber(AccountEntity accountNumber);
}
