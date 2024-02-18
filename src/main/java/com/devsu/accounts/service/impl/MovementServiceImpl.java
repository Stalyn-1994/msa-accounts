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
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import com.devsu.accounts.service.dto.response.MovementResponseDto;
import com.devsu.accounts.service.mapper.AccountServiceMapper;
import com.devsu.accounts.service.mapper.MovementServiceMapper;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
  public ResponseEntity<BaseResponseDto> update(MovementRequestDto movementRequestDto) {
    return null;
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto,
      Long identification) {
    return null;
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(Long identification) {
    return null;
  }
}