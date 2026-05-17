package com.inviq.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@AllArgsConstructor
public enum FonePayQRType {
  INTENT_QR("INTENT_QR", "Fone pay Intent QR");

  private final String code;
  private final String description;
}
