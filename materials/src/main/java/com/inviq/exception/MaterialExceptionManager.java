package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 05/05/2026
 */
public class MaterialExceptionManager extends AppException {
  public MaterialExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum MaterialError implements ExceptionType {
    MATERIAL_ID_EMPTY("INV-MAT-ID-001", "Material id is empty"),
    MATERIAL_NAME_EMPTY("INV-MAT-NAM-001", "Material name is empty"),
    ;

    private final String code;
    private final String message;
  }
}
