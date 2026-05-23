package com.inviq.service;

import com.inviq.core.dto.response.Response;
import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.LevelSubjectPaymentVerificationRequest;
import java.util.List;

/**
 * @author ripesh created on 19/05/2026
 */
public interface LevelSubjectPaymentService {

  Object initiatePayment(LevelSubjectPaymentRequest request);

  Boolean verifyPayment(LevelSubjectPaymentVerificationRequest request);

  List<Response> getUserPurchases();
}
