package com.inviq.exception;

import com.inviq.core.exception.AppException;
import com.inviq.core.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 19/05/2026
 */
public class FonePayExceptionManager extends AppException {
    public FonePayExceptionManager(ExceptionType exceptionType) {
        super(exceptionType);
    }

    @Getter
    @AllArgsConstructor
    public enum FonePayError implements ExceptionType {

        FONEPAY_LOGIN_FAILED("INV-FP-001", "Fonepay login failed"),
        ERROR_REQUESTING_FONEPAY_QR("INV-FP-002", "Error requesting fonepay qr");

        private final String code;
        private final String message;
    }
}
