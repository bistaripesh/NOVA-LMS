package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.request.LevelSubjectPaymentRequest;
import com.inviq.dto.request.LevelSubjectsAddRequest;
import com.inviq.dto.request.LevelSubjectsDeleteRequest;
import com.inviq.dto.request.LevelSubjectsUpdateRequest;
import com.inviq.service.LevelSubjectPaymentService;
import com.inviq.service.LevelSubjectsCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 05/05/2026
 */
@RestController
@RequestMapping("/level-subjects")
public class LevelSubjectsController {

  private final LevelSubjectsCrudService levelSubjectsCrudService;
  private final LevelSubjectPaymentService levelSubjectPaymentService;

  @Autowired
  public LevelSubjectsController(LevelSubjectsCrudService levelSubjectsCrudService,LevelSubjectPaymentService levelSubjectPaymentService) {
    this.levelSubjectsCrudService = levelSubjectsCrudService;
    this.levelSubjectPaymentService = levelSubjectPaymentService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createLevelSubjects(
      @RequestBody LevelSubjectsAddRequest levelSubjectsAddRequest) {
    return RestResponse.ok(this.levelSubjectsCrudService.create(levelSubjectsAddRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateLevelSubjects(
      @RequestBody LevelSubjectsUpdateRequest levelSubjectsUpdateRequest) {
    return RestResponse.ok(this.levelSubjectsCrudService.update(levelSubjectsUpdateRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteLevelSubjects(
      @ModelAttribute LevelSubjectsDeleteRequest levelSubjectsDeleteRequest) {
    return RestResponse.ok(this.levelSubjectsCrudService.delete(levelSubjectsDeleteRequest));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getLevelSubjects(
      @ModelAttribute LevelSubjectsFilter levelSubjectsFilter) {
    return RestResponse.ok(this.levelSubjectsCrudService.findAll(levelSubjectsFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getLevelSubjectsWithPagination(
      @ModelAttribute LevelSubjectsFilter levelSubjectsFilter) {
    return RestResponse.ok(this.levelSubjectsCrudService.findWithPagination(levelSubjectsFilter));
  }

  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/initiate-payment")
  public ResponseEntity<RestResponseBody> initiatePaymentForLevelSubjects(
      @RequestBody LevelSubjectPaymentRequest levelSubjectPaymentRequest) {
    return RestResponse.ok(this.levelSubjectPaymentService.initiatePayment(levelSubjectPaymentRequest));
  }
}
