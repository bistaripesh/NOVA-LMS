package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateTypeExceptionManager extends AppException {

  public AffiliateTypeExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum AffiliateTypeError implements ExceptionType {
    AFFILIATE_TYPE_ID_EMPTY("INV-AFF-TYP-ID-001", "Affiliate type id is empty"),
    AFFILIATE_TYPE_EMPTY("INV-AFF-TYP-001", "Affiliate type is empty"),
    ;

    private final String code;
    private final String message;
  }
}
