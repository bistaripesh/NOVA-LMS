package com.inviq.dto.response;

import com.inviq.core.dto.response.Response;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 22/05/2026
 */
@Getter
@Setter
public class MyPurchaseResponse implements Response {
  private String paymentId;
  private String levelSubjectId;
  private String level;
  private String subject;
  private BigDecimal amount;
  private Boolean isPaymentSuccess;
  private Instant purchasedOn;
}
