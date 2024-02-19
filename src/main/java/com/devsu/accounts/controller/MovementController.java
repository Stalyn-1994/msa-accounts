package com.devsu.accounts.controller;

import com.devsu.accounts.service.MovementService;
import com.devsu.accounts.service.dto.request.MovementRequestDto;
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
@RequestMapping("api/movements")
public class MovementController {

  final MovementService movementService;

  @PostMapping("")
  public ResponseEntity<BaseResponseDto> saveAccount(
      @RequestBody @Valid MovementRequestDto movementRequestDto) {
    return movementService.save(movementRequestDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BaseResponseDto> updateAccount(
      @RequestBody @Valid MovementRequestDto movementRequestDto,@PathVariable Long id) {
    return movementService.update(movementRequestDto,id);
  }

  @PatchMapping("/{identification}")
  public ResponseEntity<BaseResponseDto> editAccount(
      @RequestBody Map<String, Object> MovementRequestDto, @PathVariable Long identification) {
    return movementService.edit(MovementRequestDto, identification);
  }

  @DeleteMapping("/{identification}")
  public ResponseEntity<BaseResponseDto> deleteAccount(
      @PathVariable Long identification) {
    return movementService.delete(identification);
  }
}