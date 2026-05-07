package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.LevelFilter;
import com.inviq.dto.tenset.request.LevelAddRequest;
import com.inviq.dto.tenset.request.LevelDeleteRequest;
import com.inviq.dto.tenset.request.LevelUpdateRequest;
import com.inviq.serviceImpl.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 05/05/2026
 */
@RestController
@RequestMapping("/level")
public class LevelController {

  private final LevelService levelService;

  @Autowired
  public LevelController(LevelService levelService) {
    this.levelService = levelService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createLevel(
      @RequestBody LevelAddRequest levelAddRequest) {
    return RestResponse.ok(this.levelService.create(levelAddRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateLevel(
      @RequestBody LevelUpdateRequest levelUpdateRequest) {
    return RestResponse.ok(this.levelService.update(levelUpdateRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteLevel(
      @ModelAttribute LevelDeleteRequest levelDeleteRequest) {
    return RestResponse.ok(this.levelService.delete(levelDeleteRequest));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getLevels(@ModelAttribute LevelFilter levelFilter) {
    return RestResponse.ok(this.levelService.findAll(levelFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getLevelsWithPagination(
      @ModelAttribute LevelFilter levelFilter) {
    return RestResponse.ok(this.levelService.findWithPagination(levelFilter));
  }
}
