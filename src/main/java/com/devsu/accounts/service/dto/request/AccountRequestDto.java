package com.devsu.accounts.service.dto.request;

import com.devsu.accounts.domain.enums.AccountEnum;
import com.devsu.accounts.domain.validations.AccountValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class AccountRequestDto {

  @AccountValidation
  AccountEnum type;

  @NotBlank(message = "Identification is required")
  @Size(min = 8, max = 20, message = "Identification must be between 8 and 20 characters")
  String identification;
  //  String accountNumber;
  @Builder.Default
  Boolean status = false;

  @Builder.Default
  double initialBalance = 0.0;
}
