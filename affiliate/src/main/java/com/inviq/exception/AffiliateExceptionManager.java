package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateExceptionManager extends AppException {
  public AffiliateExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum AffiliateError implements ExceptionType {
    FULL_NAME_EMPTY("INV-AFF-NAM-001", "Full name is empty"),
    PHONE_NUMBER_EMPTY("INV-AFF-PHN-001", "Phone number is empty"),
    PHONE_NUMBER_INVALID("INV-AFF-PHN-002", "Phone number is invalid"),
    AFFILIATE_ID_EMPTY("INV-AFF-ID-001", "Affiliate id is empty"),
    AFFILIATE_TYPE_ID_EMPTY("INV-AFF-TYP-001", "Affiliate type id is empty"),
    ;

    private final String code;
    private final String message;
  }
}
