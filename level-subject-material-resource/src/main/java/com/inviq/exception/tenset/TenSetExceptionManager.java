package com.inviq.exception.tenset;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 06/05/2026
 */
public class TenSetExceptionManager extends AppException {
  public TenSetExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @Getter
  @AllArgsConstructor
  public enum TenSetError implements ExceptionType {
    TEN_SET_ID_EMPTY("INV-LSMR-TID-001", "Ten set id is empty"),
    LEVEL_SUBJECT_MATERIAL_ID_EMPTY("INV-LSMR-ID-001", "Level subject material id is empty"),
    PDF_FILE_NOT_FOUND("INV-LSMR-PDF-001", "PDF file not found"),
    PDF_UPLOAD_EMPTY("INV-LSMR-PDF-002", "PDF upload empty"),
    TITLE_EMPTY("INV-LSMR-PDFT-001", "Pdf title is empty"),
    ;

    private final String code;
    private final String message;
  }
}
