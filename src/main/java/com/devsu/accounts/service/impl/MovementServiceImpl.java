package com.devsu.accounts.service.impl;

import static com.devsu.accounts.helper.Helper.buildResponseEntity;
import static com.devsu.accounts.util.Constants.NOT_FOUND;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.MovementsEntity;
import com.devsu.accounts.domain.exception.NotFoundException;
import com.devsu.accounts.repository.AccountRepository;
import com.devsu.accounts.repository.MovementRepository;
import com.devsu.accounts.service.MovementService;
import com.devsu.accounts.service.dto.request.MovementRequestDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import com.devsu.accounts.service.dto.response.MovementResponseDto;
import com.devsu.accounts.service.mapper.AccountServiceMapper;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

  final AccountRepository accountRepository;
  final MovementRepository movementRepository;
  final AccountServiceMapper accountServiceMapper;

  @Override
  public ResponseEntity<BaseResponseDto> save(MovementRequestDto movementRequestDto) {
    AccountEntity account = accountRepository.findAccountEntitiesByAccountNumber(
            movementRequestDto.getAccountNumber())
        .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    double balanceTotal;
    if (!account.getMovements().isEmpty()) {
      Optional<MovementsEntity> movementsEntity = account.getMovements().stream()
          .min((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
      balanceTotal = movementsEntity.get().getBalance();
    } else {
      balanceTotal = account.getInitialBalance();
    }
    if (balanceTotal <= 0 || movementRequestDto.getType().equals("D")
        && balanceTotal < movementRequestDto.getAmount()) {
      return ResponseEntity.ok(BaseResponseDto.builder()
          .data("Saldo no disponible")
          .build());
    }
    MovementsEntity.MovementsEntityBuilder movementsEntity = MovementsEntity.builder();
    movementsEntity.date(new Date(System.currentTimeMillis()));
    movementsEntity.type(movementRequestDto.getType());
    movementsEntity.amount(movementRequestDto.getAmount());
    movementsEntity.accountNumber(account);
    if (movementRequestDto.getType().equals("D")) {
      movementsEntity.balance(balanceTotal - movementRequestDto.getAmount());
    } else {
      movementsEntity.balance(balanceTotal + movementRequestDto.getAmount());
    }
    Long id = movementRepository.save(movementsEntity.build()).getId();
    return
        buildResponseEntity(id, HttpStatus.CREATED);
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
    return buildResponseEntity(movements, HttpStatus.OK);
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