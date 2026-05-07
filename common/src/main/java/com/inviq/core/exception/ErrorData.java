package com.inviq.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ripesh created on 18/04/2025
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorData {
  private String code;
  private String message;
}
