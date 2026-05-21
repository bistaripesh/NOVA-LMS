package com.inviq.service;

import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.LevelSubjectPaymentVerificationRequest;

/**
 * @author ripesh created on 19/05/2026
 */
public interface LevelSubjectPaymentService {

  Object initiatePayment(LevelSubjectPaymentRequest request);

  Boolean verifyPayment(LevelSubjectPaymentVerificationRequest request);
}
