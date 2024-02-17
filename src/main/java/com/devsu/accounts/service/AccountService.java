package com.devsu.accounts.service;

import com.devsu.accounts.service.dto.request.AccountRequestDto;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface AccountService {

  ResponseEntity<BaseResponseDto> save(AccountRequestDto accountResponseDto);

  ResponseEntity<BaseResponseDto> update(AccountRequestDto accountResponseDto);

  ResponseEntity<BaseResponseDto> edit(Map<String, Object> customerDto, String identification);

  ResponseEntity<BaseResponseDto> delete(String identification);
}
