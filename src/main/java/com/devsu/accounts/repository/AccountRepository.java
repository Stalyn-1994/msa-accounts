package com.devsu.accounts.repository;

import com.devsu.accounts.domain.AccountEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

  Optional<AccountEntity> findAccountEntitiesByAccountNumber(String id);
}
