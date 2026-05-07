package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.filter.SubjectFilter;
import com.inviq.dto.tenset.request.SubjectAddRequest;
import com.inviq.dto.tenset.request.SubjectDeleteRequest;
import com.inviq.dto.tenset.request.SubjectUpdateRequest;
import com.inviq.service.SubjectCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 05/05/2026
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

  private final SubjectCrudService subjectCrudService;

  @Autowired
  public SubjectController(SubjectCrudService subjectCrudService) {
    this.subjectCrudService = subjectCrudService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createSubject(
      @RequestBody SubjectAddRequest subjectAddRequest) {
    return RestResponse.ok(this.subjectCrudService.create(subjectAddRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateSubject(
      @RequestBody SubjectUpdateRequest subjectUpdateRequest) {
    return RestResponse.ok(this.subjectCrudService.update(subjectUpdateRequest));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteSubject(
      @ModelAttribute SubjectDeleteRequest subjectDeleteRequest) {
    return RestResponse.ok(this.subjectCrudService.delete(subjectDeleteRequest));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getSubjects(@ModelAttribute SubjectFilter subjectFilter) {
    return RestResponse.ok(this.subjectCrudService.findAll(subjectFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getSubjectsWithPagination(
      @ModelAttribute SubjectFilter subjectFilter) {
    return RestResponse.ok(this.subjectCrudService.findWithPagination(subjectFilter));
  }
}
