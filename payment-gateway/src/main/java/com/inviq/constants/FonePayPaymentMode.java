package com.inviq.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@AllArgsConstructor
public enum FonePayPaymentMode {
  INTENT("INTENT", "Intent"),
  QR("QR", "QR"),
  ;

  private final String code;
  private final String displayName;
}
