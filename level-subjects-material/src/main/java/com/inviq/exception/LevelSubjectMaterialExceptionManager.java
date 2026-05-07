package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 06/05/2026
 */
public class LevelSubjectMaterialExceptionManager extends AppException {
  public LevelSubjectMaterialExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum LevelSubjectMaterialError implements ExceptionType {
    LEVEL_SUBJECT_MATERIAL_ID_EMPTY("INV-LSM-ID-001", "Level subject material id is empty"),
    LEVEL_SUBJECT_ID_EMPTY("INV-LSM-001", "Level subject id is empty"),
    MATERIAL_ID_EMPTY("INV-LSM-002", "Material id is empty"),
    ;

    private final String code;
    private final String message;
  }
}
