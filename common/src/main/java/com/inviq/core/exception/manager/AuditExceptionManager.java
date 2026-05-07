package com.inviq.core.exception.manager;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 17/04/2026
 */
public class AuditExceptionManager extends AppException {

  public AuditExceptionManager(ExceptionType exceptionType) {
    super(exceptionType);
  }

  @AllArgsConstructor
  @Getter
  public enum AuditError implements ExceptionType {
    AUDIT_DATA_INSERTION_FAILED("INV-DAB-INS-001", "Audit data insertion failed"),
    AUDIT_DATA_JSON_PARSE_ERROR("INV-DAB-PAR-001", "Audit data json parse error"),
    ;
    private final String code;
    private final String message;
  }
}
