package com.inviq.controller;

import com.inviq.core.access.LevelSubjectAccessService;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.LevelSubjectMaterialFilter;
import com.inviq.dto.tenset.request.LevelSubjectMaterialAddRequest;
import com.inviq.dto.tenset.request.LevelSubjectMaterialDeleteRequest;
import com.inviq.serviceImpl.LevelSubjectMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 06/05/2026
 */
@RestController
@RequestMapping("/level-subject-material")
public class LevelSubjectMaterialController {
  private final LevelSubjectMaterialService levelSubjectMaterialService;
  private final LevelSubjectAccessService levelSubjectAccessService;

  @Autowired
  public LevelSubjectMaterialController(
      LevelSubjectMaterialService levelSubjectMaterialService,
      LevelSubjectAccessService levelSubjectAccessService) {
    this.levelSubjectMaterialService = levelSubjectMaterialService;
    this.levelSubjectAccessService = levelSubjectAccessService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createLevelSubjectMaterial(
      @RequestBody LevelSubjectMaterialAddRequest levelSubjectMaterialAddRequest) {
    return RestResponse.ok(this.levelSubjectMaterialService.create(levelSubjectMaterialAddRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteLevelSubjectMaterial(
      @ModelAttribute LevelSubjectMaterialDeleteRequest levelSubjectMaterialDeleteRequest) {
    return RestResponse.ok(
        this.levelSubjectMaterialService.delete(levelSubjectMaterialDeleteRequest));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getLevelSubjectMaterials(
      @ModelAttribute LevelSubjectMaterialFilter levelSubjectMaterialFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        levelSubjectMaterialFilter.getLevelSubjectId(), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(this.levelSubjectMaterialService.findAll(levelSubjectMaterialFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getLevelSubjectMaterialsWithPagination(
      @ModelAttribute LevelSubjectMaterialFilter levelSubjectMaterialFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        levelSubjectMaterialFilter.getLevelSubjectId(), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(
        this.levelSubjectMaterialService.findWithPagination(levelSubjectMaterialFilter));
  }

  private UserPrincipal currentPrincipal() {
    return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
