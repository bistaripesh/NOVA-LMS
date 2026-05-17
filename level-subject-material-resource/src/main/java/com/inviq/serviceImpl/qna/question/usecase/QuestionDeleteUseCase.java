package com.inviq.serviceImpl.qna.question.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
import com.inviq.dao.qna.question.entity.QuestionEntity;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import com.inviq.dto.qna.question.request.QuestionDeleteRequest;
import com.inviq.dto.qna.question.response.QuestionDeleteResponse;

/**
 * @author ripesh created on 17/05/2026
 */
public class QuestionDeleteUseCase
    extends AbstractDeleteUseCase<
        QuestionEntity, QuestionDeleteRequest, QuestionDeleteResponse, QuestionFilter> {
  public QuestionDeleteUseCase(
      BaseDao<QuestionEntity, QuestionFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(QuestionDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      QuestionDeleteRequest request, QuestionEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(QuestionDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.QUESTION;
  }

  @Override
  protected QuestionFilter prepareFilter(QuestionDeleteRequest questionDeleteRequest) {
    QuestionFilter questionFilter = new QuestionFilter();
    questionFilter.setId(questionDeleteRequest.getId());
    return questionFilter;
  }

  @Override
  protected QuestionDeleteResponse prepareResponse(QuestionEntity entityToUpdate) {
    return new QuestionDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
