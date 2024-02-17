package com.devsu.accounts.domain.validations;

import com.devsu.accounts.domain.enums.AccountEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountValidator implements ConstraintValidator<AccountValidation, AccountEnum> {


  @Override
  public void initialize(AccountValidation constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(AccountEnum value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    return AccountEnum.findByCode(value.name()).isPresent();
  }
}
