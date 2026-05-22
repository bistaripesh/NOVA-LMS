package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.service.AffiliatePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ripesh created on 22/05/2026
 */
@RestController
@RequestMapping("/affiliate-payment")
public class AffiliatePaymentController {

    private final AffiliatePaymentService affiliatePaymentService;

    @Autowired
    public AffiliatePaymentController(AffiliatePaymentService affiliatePaymentService) {
        this.affiliatePaymentService = affiliatePaymentService;
    }

    @GetMapping
    public ResponseEntity<RestResponseBody> getAffiliateReferrals(
            @ModelAttribute PaymentFilter filter) {
        return RestResponse.ok(this.affiliatePaymentService.getAffiliatePayments(filter));
    }

}
