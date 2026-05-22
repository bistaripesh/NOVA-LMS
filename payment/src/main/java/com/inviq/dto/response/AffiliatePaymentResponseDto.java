package com.inviq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author ripesh created on 22/05/2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffiliatePaymentResponseDto {
    private String id;
    private String referralCode;
    private Integer commissionPercent;
    private String fullName;
    private String description;
    private Boolean isReferralCodeActive;
    private BigDecimal commissionAmount;
    private Boolean isTransactionSettled;
}