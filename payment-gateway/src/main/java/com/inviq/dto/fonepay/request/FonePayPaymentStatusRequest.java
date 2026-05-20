package com.inviq.dto.fonepay.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 20/05/2026
 */
@Getter
@Setter
public class FonePayPaymentStatusRequest {
  private String terminalId;
  private String referenceLabel;
  private String bearerToken;
}
