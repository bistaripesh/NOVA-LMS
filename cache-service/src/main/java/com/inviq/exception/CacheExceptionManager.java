package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 19/05/2026
 */
public class CacheExceptionManager extends AppException {

  public CacheExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum CacheError implements ExceptionType {
    CACHE_KEY_EMPTY("INV-CACHE-KEY-001", "Cache key is empty"),
    FAILED_TO_ADD_TO_CACHE("INV-CACHE-001", "Failed to add to cache"),
    ;

    private final String code;
    private final String message;
  }
}
