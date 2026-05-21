package com.inviq.controller;

import com.inviq.core.access.LevelSubjectAccessService;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
import com.inviq.dto.qna.answer.request.AnswerAddRequest;
import com.inviq.dto.qna.answer.request.AnswerDeleteRequest;
import com.inviq.dto.qna.answer.request.AnswerUpdateRequest;
import com.inviq.service.qna.answer.AnswerCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 17/05/2026
 */
@RestController
@RequestMapping("/answer")
public class AnswerController {

  private final AnswerCrudService answerCrudService;
  private final LevelSubjectAccessService levelSubjectAccessService;

  @Autowired
  public AnswerController(
      AnswerCrudService answerCrudService,
      LevelSubjectAccessService levelSubjectAccessService) {
    this.answerCrudService = answerCrudService;
    this.levelSubjectAccessService = levelSubjectAccessService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createAnswer(@RequestBody AnswerAddRequest request) {
    return RestResponse.ok(this.answerCrudService.create(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateAnswer(@RequestBody AnswerUpdateRequest request) {
    return RestResponse.ok(this.answerCrudService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteAnswer(
      @ModelAttribute AnswerDeleteRequest request) {
    return RestResponse.ok(this.answerCrudService.delete(request));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getAllAnswers(@ModelAttribute AnswerFilter answerFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        answerFilter.getLevelSubjectId(), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(this.answerCrudService.findAll(answerFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getAnswersWithPagination(
      @ModelAttribute AnswerFilter answerFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        answerFilter.getLevelSubjectId(), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(this.answerCrudService.findWithPagination(answerFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("qna")
  public ResponseEntity<RestResponseBody> getAns(@ModelAttribute AnswerFilter answerFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        answerFilter.getLevelSubjectId(), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(this.answerCrudService.getAnswersGroupedByQuestion(answerFilter));
  }

  private UserPrincipal currentPrincipal() {
    return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
