package com.inviq.dto.fonepay.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@Setter
public class FonePayIntentQRRequest {
  private BigDecimal amount;
  private String billId;
  private String terminalId;
  private String paymentMode;
  private String referenceLabel;
  private String fonePayQrType;
  private String bearerToken;
}
