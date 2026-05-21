package com.inviq.controller;

import com.inviq.core.context.UserPrincipal;
import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.LevelSubjectsDao;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import com.inviq.dto.qna.question.request.QuestionAddRequest;
import com.inviq.dto.qna.question.request.QuestionDeleteRequest;
import com.inviq.dto.qna.question.request.QuestionUpdateRequest;
import com.inviq.service.LevelSubjectAccessService;
import com.inviq.service.qna.question.QuestionCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 17/05/2026
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

  private final QuestionCrudService questionCrudService;
  private final LevelSubjectAccessService levelSubjectAccessService;
  private final LevelSubjectsDao levelSubjectsDao;

  @Autowired
  public QuestionController(
      QuestionCrudService questionCrudService,
      LevelSubjectAccessService levelSubjectAccessService,
      LevelSubjectsDao levelSubjectsDao) {
    this.questionCrudService = questionCrudService;
    this.levelSubjectAccessService = levelSubjectAccessService;
    this.levelSubjectsDao = levelSubjectsDao;
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','WRITER')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createQuestion(@RequestBody QuestionAddRequest request) {
    return RestResponse.ok(this.questionCrudService.create(request));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','WRITER')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateQuestion(
      @RequestBody QuestionUpdateRequest request) {
    return RestResponse.ok(this.questionCrudService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteQuestion(
      @ModelAttribute QuestionDeleteRequest request) {
    return RestResponse.ok(this.questionCrudService.delete(request));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getQuestions(
      @ModelAttribute QuestionFilter questionFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        resolveLevelSubjectId(questionFilter), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(this.questionCrudService.findAll(questionFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER','WRITER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getQuestionsWithPagination(
      @ModelAttribute QuestionFilter questionFilter) {
    UserPrincipal principal = currentPrincipal();
    this.levelSubjectAccessService.ensureAccess(
        resolveLevelSubjectId(questionFilter), principal.getUserId(), principal.getUserType());
    return RestResponse.ok(this.questionCrudService.findWithPagination(questionFilter));
  }

  private String resolveLevelSubjectId(QuestionFilter filter) {
    if (StringUtil.isNotBlankOrNull(filter.getLevelSubjectId())) return filter.getLevelSubjectId();

    if (StringUtil.isNotBlankOrNull(filter.getLevelId())
        && StringUtil.isNotBlankOrNull(filter.getSubjectId())) {
      LevelSubjectsFilter lsFilter = new LevelSubjectsFilter();
      lsFilter.setLevelId(filter.getLevelId());
      lsFilter.setSubjectId(filter.getSubjectId());
      return this.levelSubjectsDao.findOne(lsFilter).map(e -> e.getId()).orElse(null);
    }

    return null;
  }

  private UserPrincipal currentPrincipal() {
    return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
