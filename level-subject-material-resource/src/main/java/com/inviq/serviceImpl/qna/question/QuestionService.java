package com.inviq.serviceImpl.qna.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Pageable;
import com.inviq.dao.qna.question.QuestionDao;
import com.inviq.dao.qna.question.converter.QuestionEntityConverter;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import com.inviq.dto.qna.question.request.QuestionAddRequest;
import com.inviq.dto.qna.question.request.QuestionDeleteRequest;
import com.inviq.dto.qna.question.request.QuestionUpdateRequest;
import com.inviq.service.qna.question.QuestionCrudService;
import com.inviq.serviceImpl.qna.question.usecase.QuestionAddUseCase;
import com.inviq.serviceImpl.qna.question.usecase.QuestionDeleteUseCase;
import com.inviq.serviceImpl.qna.question.usecase.QuestionUpdateUseCase;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ripesh created on 17/05/2026
 */
@Service
public class QuestionService implements QuestionCrudService {

  private final QuestionDao questionDao;
  private final AuditDao auditDao;
  private final ObjectMapper objectMapper;

  @Autowired
  public QuestionService(QuestionDao questionDao, AuditDao auditDao, ObjectMapper objectMapper) {
    this.questionDao = questionDao;
    this.auditDao = auditDao;
    this.objectMapper = objectMapper;
  }

  @Transactional
  @Override
  public Response create(QuestionAddRequest request) {
    return new QuestionAddUseCase(this.questionDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response update(QuestionUpdateRequest request) {
    return new QuestionUpdateUseCase(this.questionDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Transactional
  @Override
  public Response delete(QuestionDeleteRequest request) {
    return new QuestionDeleteUseCase(this.questionDao, this.auditDao, this.objectMapper)
        .execute(request);
  }

  @Override
  public Optional<Response> findOne(QuestionFilter request) {
    return this.questionDao
        .findOne(request)
        .map(entity -> new QuestionEntityConverter().getResponse(entity));
  }

  @Override
  public List<Response> findAll(QuestionFilter request) {
    return new QuestionEntityConverter().getResponses(this.questionDao.findAll(request));
  }

  @Override
  public Pageable findWithPagination(QuestionFilter questionFilter) {
    return this.questionDao.findWithPagination(questionFilter);
  }
}
