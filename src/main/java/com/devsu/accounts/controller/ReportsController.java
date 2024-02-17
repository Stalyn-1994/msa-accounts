package com.devsu.accounts.controller;

import com.devsu.accounts.service.MovementService;
import com.devsu.accounts.service.dto.response.BaseResponseDto;
import java.time.LocalDate;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reports")
public class ReportsController {

  final MovementService movementService;

  @GetMapping("")
  public ResponseEntity<BaseResponseDto> getReportMovements(
      @RequestParam(name = "dateInit") LocalDate dateInit
      , @RequestParam(name = "dateEnd") LocalDate dateEnd
      , @RequestParam(name = "customer") String customer) {
    return movementService.get(dateInit, dateEnd, customer);
  }
}