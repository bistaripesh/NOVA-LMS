package com.inviq.controller;

import com.inviq.core.response.RestResponse;
import com.inviq.core.response.RestResponseBody;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import com.inviq.dto.qna.question.request.QuestionAddRequest;
import com.inviq.dto.qna.question.request.QuestionDeleteRequest;
import com.inviq.dto.qna.question.request.QuestionUpdateRequest;
import com.inviq.service.qna.QuestionCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ripesh created on 17/05/2026
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

  private final QuestionCrudService questionCrudService;

  @Autowired
  public QuestionController(QuestionCrudService questionCrudService) {
    this.questionCrudService = questionCrudService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<RestResponseBody> createQuestion(@RequestBody QuestionAddRequest request) {
    return RestResponse.ok(this.questionCrudService.create(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping
  public ResponseEntity<RestResponseBody> updateQuestion(@RequestBody QuestionUpdateRequest request) {
    return RestResponse.ok(this.questionCrudService.update(request));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping
  public ResponseEntity<RestResponseBody> deleteQuestion(@ModelAttribute QuestionDeleteRequest request) {
    return RestResponse.ok(this.questionCrudService.delete(request));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping("/all")
  public ResponseEntity<RestResponseBody> getQuestions(@ModelAttribute QuestionFilter questionFilter) {
    return RestResponse.ok(this.questionCrudService.findAll(questionFilter));
  }

  @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
  @GetMapping
  public ResponseEntity<RestResponseBody> getQuestionsWithPagination(
      @ModelAttribute QuestionFilter questionFilter) {
    return RestResponse.ok(this.questionCrudService.findWithPagination(questionFilter));
  }

}
