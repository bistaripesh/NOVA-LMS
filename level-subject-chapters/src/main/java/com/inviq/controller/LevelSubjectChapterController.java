package com.inviq.controller;

import com.inviq.core.context.UserPrincipal;
import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.affiliate.filter.LevelSubjectChapterFilter;
import com.inviq.dto.request.LevelSubjectChapterAddRequest;
import com.inviq.dto.request.LevelSubjectChapterDeleteRequest;
import com.inviq.dto.request.LevelSubjectChapterUpdateRequest;
import com.inviq.service.LevelSubjectAccessService;
import com.inviq.service.LevelSubjectChapterCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 07/05/2026
 */
@RestController
@RequestMapping("/level-subject-chapters")
public class LevelSubjectChapterController {

  private final LevelSubjectChapterCrudService levelSubjectChapterCrudService;
  private final LevelSubjectAccessService levelSubjectAccessService;

  @Autowired
  public LevelSubjectChapterController(
      LevelSubjectChapterCrudService levelSubjectChapterCrudService,
      LevelSubjectAccessService levelSubjectAccessService) {
    this.levelSubjectChapterCrudService = levelSubjectChapterCrudService;
    this.levelSubjectAccessService = levelSubjectAccessService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createLevelSubjectChapter(
      @RequestBody LevelSubjectChapterAddRequest request) {
    return RestResponse.ok(this.levelSubjectChapterCrudService.create(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateLevelSubjectChapter(
      @RequestBody LevelSubjectChapterUpdateRequest request) {
    return RestResponse.ok(this.levelSubjectChapterCrudService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteLevelSubjectChapter(
      @ModelAttribute LevelSubjectChapterDeleteRequest request) {
    return RestResponse.ok(this.levelSubjectChapterCrudService.delete(request));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getLevelSubjectChapters(
      @ModelAttribute LevelSubjectChapterFilter levelSubjectChapterFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        levelSubjectChapterFilter.getLevelSubjectId(), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(this.levelSubjectChapterCrudService.findAll(levelSubjectChapterFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getLevelSubjectChaptersWithPagination(
      @ModelAttribute LevelSubjectChapterFilter levelSubjectChapterFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        levelSubjectChapterFilter.getLevelSubjectId(), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(
        this.levelSubjectChapterCrudService.findWithPagination(levelSubjectChapterFilter));
  }

  private UserPrincipal currentPrincipal() {
    return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
