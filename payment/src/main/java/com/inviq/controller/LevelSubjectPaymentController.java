package com.inviq.controller;

import com.inviq.core.context.UserPrincipal;
import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.LevelSubjectPaymentVerificationRequest;
import com.inviq.service.LevelSubjectPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 21/05/2026
 */
@RestController
@RequestMapping("/level-subjects")
public class LevelSubjectPaymentController {

  private final LevelSubjectPaymentService levelSubjectPaymentService;

  @Autowired
  public LevelSubjectPaymentController(LevelSubjectPaymentService levelSubjectPaymentService) {
    this.levelSubjectPaymentService = levelSubjectPaymentService;
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/initiate-payment")
  public ResponseEntity<RestResponseBody> initiatePaymentForLevelSubjects(
      @RequestBody LevelSubjectPaymentRequest levelSubjectPaymentRequest) {
    return RestResponse.ok(this.levelSubjectPaymentService.initiatePayment(levelSubjectPaymentRequest));
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/verify-payment")
  public ResponseEntity<RestResponseBody> verifyPaymentForLevelSubjects(
          @RequestBody LevelSubjectPaymentVerificationRequest paymentVerificationRequest) {
    return RestResponse.ok(this.levelSubjectPaymentService.verifyPayment(paymentVerificationRequest));
  }

  @GetMapping("/my-purchases")
  public ResponseEntity<RestResponseBody> getMyPurchases() {
    UserPrincipal principal =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return RestResponse.ok(this.levelSubjectPaymentService.getMyPurchases(principal.getUserId()));
  }
}
