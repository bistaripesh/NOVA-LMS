package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
import com.inviq.dto.affiliate.request.AffiliateAddRequest;
import com.inviq.dto.affiliate.request.AffiliateDeleteRequest;
import com.inviq.dto.affiliate.request.AffiliateUpdateRequest;
import com.inviq.service.affiliate.AffiliateCurdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ripesh created on 11/05/2026
 */
@RestController
@RequestMapping("/affiliate")
public class AffiliateController {

  private final AffiliateCurdService affiliateCurdService;

  @Autowired
  public AffiliateController(AffiliateCurdService affiliateCurdService) {
    this.affiliateCurdService = affiliateCurdService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RestResponseBody> createAffiliate(
      @RequestPart("data") AffiliateAddRequest request, @RequestPart("file") MultipartFile file) {
    request.setProfilePictureMultipart(file);
    return RestResponse.ok(this.affiliateCurdService.create(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RestResponseBody> updateAffiliate(
      @RequestPart("data") AffiliateUpdateRequest request,
      @RequestPart("file") MultipartFile file) {
    request.setProfilePictureMultipart(file);
    return RestResponse.ok(this.affiliateCurdService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteAffiliate(
      @ModelAttribute AffiliateDeleteRequest request) {
    return RestResponse.ok(this.affiliateCurdService.delete(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getAllAffiliate(
      @ModelAttribute AffiliateFilter affiliateFilter) {
    return RestResponse.ok(this.affiliateCurdService.findAll(affiliateFilter));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getAffiliateWithPagination(
      @ModelAttribute AffiliateFilter affiliateFilter) {
    return RestResponse.ok(this.affiliateCurdService.findWithPagination(affiliateFilter));
  }
}
