package com.devsu.accounts.repository;

import com.devsu.accounts.domain.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

//  Optional<CustomerEntity> findCustomerEntitiesByClientId(String id);
}
