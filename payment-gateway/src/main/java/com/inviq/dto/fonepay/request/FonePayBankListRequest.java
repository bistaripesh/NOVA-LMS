package com.inviq.dto.fonepay.request;

import com.inviq.constants.FonePayPaymentMode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 14/05/2026
 */
@Getter
@Setter
public class FonePayBankListRequest {
  private String bearerToken;
  private FonePayPaymentMode paymentMode;
  private String mobileNumber;
}
