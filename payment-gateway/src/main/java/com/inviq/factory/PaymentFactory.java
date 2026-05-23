package com.inviq.factory;

import com.inviq.PaymentGateWays;
import com.inviq.core.exception.AppException;
import com.inviq.service.fonepay.FonePayService;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 19/05/2026
 */
@Service
public class PaymentFactory {

  private final FonePayService fonePayService;

  public PaymentFactory(FonePayService fonePayService) {
    this.fonePayService = fonePayService;
  }

  public Object getPaymentGateway(PaymentGateWays paymentGateway) {
    switch (paymentGateway) {
      case FONE_PAY -> {
        return fonePayService;
      }
      default -> {
        throw new AppException("EW", "Payment Gateway not supported");
      }
    }
  }
}
