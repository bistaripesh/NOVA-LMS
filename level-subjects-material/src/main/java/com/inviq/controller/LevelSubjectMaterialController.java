package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.LevelSubjectMaterialFilter;
import com.inviq.dto.tenset.request.LevelSubjectMaterialAddRequest;
import com.inviq.dto.tenset.request.LevelSubjectMaterialDeleteRequest;
import com.inviq.serviceImpl.LevelSubjectMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 06/05/2026
 */
@RestController
@RequestMapping("/level-subject-material")
public class LevelSubjectMaterialController {
  private final LevelSubjectMaterialService levelSubjectMaterialService;

  @Autowired
  public LevelSubjectMaterialController(LevelSubjectMaterialService levelSubjectMaterialService) {
    this.levelSubjectMaterialService = levelSubjectMaterialService;
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

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getLevelSubjectMaterials(
      @ModelAttribute LevelSubjectMaterialFilter levelSubjectMaterialFilter) {
    return RestResponse.ok(this.levelSubjectMaterialService.findAll(levelSubjectMaterialFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getLevelSubjectMaterialsWithPagination(
      @ModelAttribute LevelSubjectMaterialFilter levelSubjectMaterialFilter) {
    return RestResponse.ok(
        this.levelSubjectMaterialService.findWithPagination(levelSubjectMaterialFilter));
  }
}
