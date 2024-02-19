package com.devsu.accounts.controller;

import com.devsu.accounts.service.AccountService;
import com.devsu.accounts.service.dto.request.AccountRequestDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class AccountController {

  final AccountService accountService;

  @PostMapping("")
  public ResponseEntity<BaseResponseDto> saveAccount(
      @RequestBody @Valid AccountRequestDto accountRequestDto) {
    return accountService.save(accountRequestDto);
  }

  @PutMapping("")
  public ResponseEntity<BaseResponseDto> updateAccount(
      @RequestBody @Valid AccountRequestDto accountRequestDto) {
    return accountService.update(accountRequestDto);
  }

  @PatchMapping("/{identification}")
  public ResponseEntity<BaseResponseDto> editAccount(
      @RequestBody Map<String, Object> accountRequestDto, @PathVariable String identification) {
    return accountService.edit(accountRequestDto, identification);
  }


  @DeleteMapping("/{identification}")
  public ResponseEntity<BaseResponseDto> deleteAccount(
      @PathVariable String identification) {
    return accountService.delete(identification);
  }
}