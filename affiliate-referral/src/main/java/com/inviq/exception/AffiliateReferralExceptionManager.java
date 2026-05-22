package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 13/05/2026
 */
public class AffiliateReferralExceptionManager extends AppException {
  public AffiliateReferralExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum AffiliateReferralError implements ExceptionType {
    AFFILIATE_REFERRAL_ID_EMPTY("INV-AFF-REF-001", "Affiliate referral id is empty"),
    AFFILIATE_ID_EMPTY("INV-AFF-ID-001", "Affiliate id is empty"),
    COMMISSION_PERCENTAGE_EMPTY("INV-AFF-COM-001", "Commission percentage is empty"),
    COMMISSION_PERCENTAGE_INVALID("INV-AFF-COM-002", "Commission percentage is invalid"),
    TRANSACTION_SETTLEMENT_STATUS_EMPTY("INV-AFF-COM-003", "Transaction status is empty"),
    ;

    private final String code;
    private final String message;
  }
}
