package com.devsu.accounts.repository;

import com.devsu.accounts.domain.MovementsEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends CrudRepository<MovementsEntity, Long> {

  List<MovementsEntity> findByDateBetween(Date dateInit, Date endDate);
}
