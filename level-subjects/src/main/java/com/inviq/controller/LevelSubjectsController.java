package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.tenset.request.LevelSubjectsAddRequest;
import com.inviq.dto.tenset.request.LevelSubjectsDeleteRequest;
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

  @Autowired
  public LevelSubjectsController(LevelSubjectsCrudService levelSubjectsCrudService) {
    this.levelSubjectsCrudService = levelSubjectsCrudService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createLevelSubjects(
      @RequestBody LevelSubjectsAddRequest levelSubjectsAddRequest) {
    return RestResponse.ok(this.levelSubjectsCrudService.create(levelSubjectsAddRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteLevelSubjects(
      @ModelAttribute LevelSubjectsDeleteRequest levelSubjectsDeleteRequest) {
    return RestResponse.ok(this.levelSubjectsCrudService.delete(levelSubjectsDeleteRequest));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getLevelSubjects(
      @ModelAttribute LevelSubjectsFilter levelSubjectsFilter) {
    return RestResponse.ok(this.levelSubjectsCrudService.findAll(levelSubjectsFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getLevelSubjectsWithPagination(
      @ModelAttribute LevelSubjectsFilter levelSubjectsFilter) {
    return RestResponse.ok(this.levelSubjectsCrudService.findWithPagination(levelSubjectsFilter));
  }
}
