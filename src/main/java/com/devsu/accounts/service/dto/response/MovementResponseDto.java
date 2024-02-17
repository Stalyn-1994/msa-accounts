package com.devsu.accounts.service.dto.response;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementResponseDto {

  private Date date;
  private String customer;
  private String accountNumber;
  private String type;
  private double initialBalance;
  private Boolean status;
  private double amount;
  private double balance;
}
