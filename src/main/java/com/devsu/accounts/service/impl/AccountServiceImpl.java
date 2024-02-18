package com.devsu.accounts.service.impl;

import static com.devsu.accounts.helper.Helper.buildResponseDto;
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
import java.util.Optional;
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
//    AccountEntity customerEntity = accountRepository
//        .findAccountEntitiesByAccountNumber(accountResponseDto.getAccountNumber())
//        .orElseThrow(() -> new NotFoundException(NOT_FOUND));
//    Long customerId = accountRepository.save(
//        accountServiceMapper.toAccountEntity(accountResponseDto)).getId();
//    return buildResponseEntity(AccountResponseDto.builder()
//        .customerId(String.valueOf(customerId))
//        .build(), HttpStatus.OK);
    return null;
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto,
      String identification) {
    Optional<AccountEntity> customerEntity = accountRepository.findAccountEntitiesByAccountNumber(
        identification);
    if (customerEntity.isPresent()) {
      customerDto.forEach((key, value) -> {
        Field field = ReflectionUtils.findField(AccountEntity.class, key);
        if (field != null) {
          field.setAccessible(true);
          ReflectionUtils.setField(field, customerEntity.get(), value);
        }
      });
      Long customerId = accountRepository.save(customerEntity.get()).getId();
      return buildResponseDto(AccountResponseDto.builder()
          .accountNumber(String.valueOf(customerId))
          .build(), HttpStatus.OK);
    }
    throw new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND);
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(String identification) {
    Optional<AccountEntity> customerEntity = accountRepository.findAccountEntitiesByAccountNumber(
        identification);
    if (customerEntity.isPresent()) {
      accountRepository.delete(customerEntity.get());
      return buildResponseDto(
          AccountResponseDto.builder().accountNumber(String.valueOf(identification)).build(),
          HttpStatus.NO_CONTENT);
    }
    throw new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND);
  }
}