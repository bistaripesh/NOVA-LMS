package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dto.request.AffiliateReferralAddRequest;
import com.inviq.dto.request.AffiliateReferralDeleteRequest;
import com.inviq.dto.request.AffiliateReferralUpdateRequest;
import com.inviq.service.AffiliateReferralCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 13/05/2026
 */
@RestController
@RequestMapping("/affiliate-referral")
public class AffiliateReferralController {

  private final AffiliateReferralCrudService affiliateReferralCrudService;

  @Autowired
  public AffiliateReferralController(AffiliateReferralCrudService affiliateReferralCrudService) {
    this.affiliateReferralCrudService = affiliateReferralCrudService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createAffiliateReferral(
      @RequestBody AffiliateReferralAddRequest request) {
    return RestResponse.ok(this.affiliateReferralCrudService.create(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateAffiliateReferral(
      @RequestBody AffiliateReferralUpdateRequest request) {
    return RestResponse.ok(this.affiliateReferralCrudService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/remove")
  public ResponseEntity<RestResponseBody> deleteAffiliateReferral(
      @ModelAttribute AffiliateReferralDeleteRequest request) {
    return RestResponse.ok(this.affiliateReferralCrudService.delete(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getAffiliateReferrals(
      @ModelAttribute AffiliateReferralFilter filter) {
    return RestResponse.ok(this.affiliateReferralCrudService.findAll(filter));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getAffiliateReferralsWithPagination(
      @ModelAttribute AffiliateReferralFilter filter) {
    return RestResponse.ok(this.affiliateReferralCrudService.findWithPagination(filter));
  }
}
