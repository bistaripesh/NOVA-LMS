package com.inviq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**w
 * @author ripesh created on 22/05/2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPurchaseResponse {
  private String paymentId;
  private String levelSubjectId;
  private String level;
  private String subject;
  private BigDecimal amount;
  private Boolean isPaymentSuccess;
  private Instant purchasedOn;
}
