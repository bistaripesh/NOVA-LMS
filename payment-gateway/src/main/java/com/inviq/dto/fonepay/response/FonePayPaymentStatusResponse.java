package com.inviq.dto.fonepay.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 20/05/2026
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FonePayPaymentStatusResponse {
  private String prn;
  private String merchantCode;
  private String paymentStatus;
  private long fonepayTraceId;
  private String requestedAmount;
  private String totalTransactionAmount;
  private String paymentMessage;
}
