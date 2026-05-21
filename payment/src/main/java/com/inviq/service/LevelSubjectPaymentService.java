package com.inviq.service;

import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.LevelSubjectPaymentVerificationRequest;
import com.inviq.dto.response.MyPurchaseResponse;
import java.util.List;

/**
 * @author ripesh created on 19/05/2026
 */
public interface LevelSubjectPaymentService {

  Object initiatePayment(LevelSubjectPaymentRequest request);

  Boolean verifyPayment(LevelSubjectPaymentVerificationRequest request);

  List<MyPurchaseResponse> getMyPurchases(String userId);
}
