package com.inviq;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ripesh created on 19/05/2026
 */
@Getter
@AllArgsConstructor
public enum PaymentGateWays {

    FONE_PAY("FONE_PAY", "FonePay"),;

    private final String code;
    private final String displayName;
}
