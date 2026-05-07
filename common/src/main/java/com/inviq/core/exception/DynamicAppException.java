package com.inviq.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 23/04/2026
 */
@Getter
@Setter
public class DynamicAppException extends RuntimeException {
  private String code;
  private String message;

  public DynamicAppException(String message) {
    super(message);
  }

  public DynamicAppException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }
}
