package com.devsu.accounts.service.impl;

import static com.devsu.accounts.helper.Helper.buildResponseDto;
import static com.devsu.accounts.util.Constants.NOT_FOUND;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.MovementsEntity;
import com.devsu.accounts.domain.exception.GenericException;
import com.devsu.accounts.repository.AccountRepository;
import com.devsu.accounts.repository.MovementRepository;
import com.devsu.accounts.service.MovementService;
import com.devsu.accounts.service.dto.request.MovementRequestDto;
import com.devsu.accounts.service.dto.response.AccountResponseDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import com.devsu.accounts.service.dto.response.MovementResponseDto;
import com.devsu.accounts.service.mapper.AccountServiceMapper;
import com.devsu.accounts.service.mapper.MovementServiceMapper;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

  final AccountRepository accountRepository;
  final MovementRepository movementRepository;
  final AccountServiceMapper accountServiceMapper;
  final MovementServiceMapper movementServiceMapper;

  @Override
  public ResponseEntity<BaseResponseDto> save(MovementRequestDto movementRequestDto) {
    AccountEntity account = accountRepository.findAccountEntitiesByAccountNumber(
            movementRequestDto.getAccountNumber())
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    double balanceTotal = movementServiceMapper.calculateBalanceTotal(account);
    MovementsEntity movementsEntity = movementServiceMapper.buildMoveEntity(movementRequestDto
        , balanceTotal
        , account);
    Long id = movementRepository.save(movementsEntity).getId();
    return
        buildResponseDto(id, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<BaseResponseDto> update(MovementRequestDto movementRequestDto, Long id) {
    MovementsEntity movementsEntity = movementRepository
        .findById(id)
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    List<MovementsEntity> movementsEntities = movementsEntity.getAccountNumber().getMovements();
    List<MovementsEntity> beforeEvents = movementsEntities.stream()
        .filter(e -> e.getDate().before(movementsEntity.getDate()))
        .toList();
    AccountEntity accountEntity = AccountEntity.builder().movements(beforeEvents).build();
    List<MovementsEntity> afterEvents = movementsEntities.stream()
        .filter(e -> e.getDate().after(movementsEntity.getDate()) || e.getDate()
            .equals(movementsEntity.getDate()))
        .toList();
    double balanceTotal = movementServiceMapper.calculateBalanceTotal(
        accountEntity);
    MovementsEntity movementUpdated = movementServiceMapper.buildMoveEntity(movementRequestDto
        , balanceTotal
        , movementsEntity.getAccountNumber());
    movementsEntity.setType(movementUpdated.getType());
    movementsEntity.setBalance(movementUpdated.getBalance());
    movementsEntity.setAmount(movementUpdated.getAmount());
    //MovementsEntity idMovement = movementRepository.save(movementsEntity);
    afterEvents = afterEvents.stream()
        .sorted(Comparator.comparing(MovementsEntity::getDate)).collect(Collectors.toList());
    List<MovementsEntity> movementsEntities1 = new ArrayList<>();
    for (int i = 0; i < afterEvents.size() - 1; i++) {
      MovementsEntity movements = afterEvents.get(i + 1);
      movements.setBalance(movements.getAmount() + afterEvents.get(i).getBalance());
      movementsEntities1.add(movements);
    }
    movementsEntities1.add(movementsEntity);
    movementRepository.saveAll(movementsEntities1);
    return buildResponseDto(AccountResponseDto.builder()
        .accountNumber(String.valueOf(id))
        .build(), HttpStatus.OK);

  }

  @Override
  public ResponseEntity<BaseResponseDto> get(LocalDate initDate, LocalDate endDate,
      String customer) {
    List<MovementsEntity> movementsEntities = movementRepository.findByDateBetween(
        Timestamp.valueOf(initDate.atStartOfDay())
        , Timestamp.valueOf(endDate.atStartOfDay()));
    List<MovementResponseDto> movements = movementsEntities.stream().filter(
            movementsEntity -> movementsEntity.getAccountNumber().getCustomer().equals(customer))
        .map(accountServiceMapper::toAccountResponseDto).collect(
            Collectors.toList());
    return buildResponseDto(movements, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> movementsFields,
      Long idMovement) {
    MovementsEntity movementsEntity = movementRepository
        .findById(idMovement)
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    movementsFields.forEach((key, value) -> {
      Field field = ReflectionUtils.findField(MovementsEntity.class, key);
      if (field != null) {
        field.setAccessible(true);
        ReflectionUtils.setField(field, movementsEntity, value);
      }
    });
    Long customerId = movementRepository.save(movementsEntity).getId();
    return buildResponseDto(AccountResponseDto.builder()
        .accountNumber(String.valueOf(customerId))
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(Long idMovement) {
    MovementsEntity movementsEntity = movementRepository
        .findById(idMovement)
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    movementRepository.delete(movementsEntity);
    return buildResponseDto(
        AccountResponseDto.builder().accountNumber(String.valueOf(idMovement)).build(),
        HttpStatus.NO_CONTENT);
  }
}