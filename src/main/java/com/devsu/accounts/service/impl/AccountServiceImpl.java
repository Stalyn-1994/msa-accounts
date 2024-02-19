package com.devsu.accounts.service.impl;

import static com.devsu.accounts.helper.Helper.buildResponseDto;
import static com.devsu.accounts.util.Constants.ACCOUNT_WITH_MOVEMENTS;
import static com.devsu.accounts.util.Constants.NOT_FOUND;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.exception.GenericException;
import com.devsu.accounts.domain.exception.InternalErrorException;
import com.devsu.accounts.repository.AccountRepository;
import com.devsu.accounts.service.AccountService;
import com.devsu.accounts.service.dto.request.AccountRequestDto;
import com.devsu.accounts.service.dto.response.AccountResponseDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import com.devsu.accounts.service.mapper.AccountServiceMapper;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  final AccountRepository accountRepository;
  final AccountServiceMapper accountServiceMapper;


  @Override
  public ResponseEntity<BaseResponseDto> save(AccountRequestDto accountRequestDto) {
    try {
      String accountNumber = accountRepository.save(
              accountServiceMapper.toAccountEntity(accountRequestDto))
          .getAccountNumber();
      return buildResponseDto(AccountResponseDto.builder()
          .accountNumber(accountNumber)
          .build(), HttpStatus.CREATED);
    } catch (Exception exception) {
      throw new InternalErrorException(exception.getMessage());
    }
  }

  @Override
  public ResponseEntity<BaseResponseDto> update(AccountRequestDto accountResponseDto) {
    AccountEntity customerEntity = accountRepository
        .findAccountEntitiesByAccountNumber(accountResponseDto.getAccountNumber())
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    String accountNumber = accountRepository.save(
            accountServiceMapper.toAccountEntityUpdated(accountResponseDto, customerEntity.getId()))
        .getAccountNumber();
    return buildResponseDto(AccountResponseDto.builder()
        .accountNumber(String.valueOf(accountNumber))
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> accountFields,
      String accountNumber) {
    AccountEntity accountEntity = accountRepository.findAccountEntitiesByAccountNumber(
        accountNumber).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    accountFields.forEach((key, value) -> {
      Field field = ReflectionUtils.findField(AccountEntity.class, key);
      if (field != null) {
        field.setAccessible(true);
        ReflectionUtils.setField(field, accountEntity, value);
      }
    });
    Long customerId = accountRepository.save(accountEntity).getId();
    return buildResponseDto(AccountResponseDto.builder()
        .accountNumber(String.valueOf(customerId))
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(String accountNumber) {
    AccountEntity accountEntity = accountRepository.findAccountEntitiesByAccountNumber(
        accountNumber).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    if (accountEntity.getMovements().isEmpty()) {
      accountRepository.delete(accountEntity);
      return ResponseEntity.noContent().build();
    }
    throw new GenericException(HttpStatus.BAD_REQUEST, ACCOUNT_WITH_MOVEMENTS);
  }
}
