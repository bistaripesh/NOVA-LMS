package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.affiliateType.filter.AffiliateTypeFilter;
import com.inviq.dto.affiliateType.request.AffiliateTypeAddRequest;
import com.inviq.dto.affiliateType.request.AffiliateTypeDeleteRequest;
import com.inviq.dto.affiliateType.request.AffiliateTypeUpdateRequest;
import com.inviq.service.affiliateType.AffiliateTypeCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 12/05/2026
 */
@RestController
@RequestMapping("/affiliate-type")
public class AffiliateTypeController {

  private final AffiliateTypeCrudService affiliateTypeCrudService;

  @Autowired
  public AffiliateTypeController(AffiliateTypeCrudService affiliateTypeCrudService) {
    this.affiliateTypeCrudService = affiliateTypeCrudService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createAffiliateType(
      @RequestBody AffiliateTypeAddRequest request) {
    return RestResponse.ok(this.affiliateTypeCrudService.create(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateAffiliateType(
      @RequestBody AffiliateTypeUpdateRequest request) {
    return RestResponse.ok(this.affiliateTypeCrudService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteAffiliateType(
      @ModelAttribute AffiliateTypeDeleteRequest request) {
    return RestResponse.ok(this.affiliateTypeCrudService.delete(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getAllAffiliateTypes(
      @ModelAttribute AffiliateTypeFilter affiliateTypeFilter) {
    return RestResponse.ok(this.affiliateTypeCrudService.findAll(affiliateTypeFilter));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getAffiliateTypesWithPagination(
      @ModelAttribute AffiliateTypeFilter affiliateTypeFilter) {
    return RestResponse.ok(this.affiliateTypeCrudService.findWithPagination(affiliateTypeFilter));
  }
}
