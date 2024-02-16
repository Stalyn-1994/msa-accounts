package com.devsu.accounts.service.impl;

import static com.devsu.accounts.helper.Helper.buildResponseEntity;
import static com.devsu.accounts.util.Constants.NOT_FOUND;

import com.devsu.accounts.domain.AccountEntity;
import com.devsu.accounts.domain.exception.NotFoundException;
import com.devsu.accounts.repository.AccountRepository;
import com.devsu.accounts.service.AccountService;
import com.devsu.accounts.service.dto.request.AccountRequestDto;
import com.devsu.accounts.service.dto.response.AccountResponseDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import com.devsu.accounts.service.mapper.AccountServiceMapper;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  final AccountRepository accountRepository;
  final AccountServiceMapper accountServiceMapper;


  @Override
  public ResponseEntity<BaseResponseDto> save(AccountRequestDto accountRequestDto) {
    Long id = accountRepository.save(accountServiceMapper.toAccountEntity(accountRequestDto))
        .getId();

    return buildResponseEntity(AccountResponseDto.builder()
        .customerId(String.valueOf(id))
        .build(), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<BaseResponseDto> update(AccountRequestDto accountResponseDto) {
    Optional<AccountEntity> customerEntity = accountRepository
        .findAccountEntitiesById(accountResponseDto.getCustomerId());
    if (customerEntity.isPresent()) {
      Long customerId = accountRepository.save(
          accountServiceMapper.toAccountEntity(accountResponseDto)).getId();
      return buildResponseEntity(AccountResponseDto.builder()
          .customerId(String.valueOf(customerId))
          .build(), HttpStatus.OK);
    }
    throw new NotFoundException(NOT_FOUND);
  }

  @Override
  public ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto,
      Long identification) {
    Optional<AccountEntity> customerEntity = accountRepository.findAccountEntitiesById(
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
      return buildResponseEntity(AccountResponseDto.builder()
          .customerId(String.valueOf(customerId))
          .build(), HttpStatus.OK)
          ;
    }
    throw new NotFoundException(NOT_FOUND);
  }

  @Override
  public ResponseEntity<BaseResponseDto> delete(Long identification) {
    Optional<AccountEntity> customerEntity = accountRepository.findAccountEntitiesById(
        identification);
    if (customerEntity.isPresent()) {
      accountRepository.delete(customerEntity.get());
      return buildResponseEntity(
          AccountResponseDto.builder().customerId(String.valueOf(identification)).build(),
          HttpStatus.NO_CONTENT);
    }
    throw new NotFoundException(NOT_FOUND);
  }
}