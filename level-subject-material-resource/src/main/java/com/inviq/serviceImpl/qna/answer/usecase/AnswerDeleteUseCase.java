package com.inviq.serviceImpl.qna.answer.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.exception.AppException;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.qna.answer.entity.AnswerEntity;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
import com.inviq.dto.qna.answer.request.AnswerDeleteRequest;
import com.inviq.dto.qna.answer.response.AnswerDeleteResponse;
import com.inviq.exception.qna.answer.AnswerExceptionManager;

/**
 * @author ripesh created on 17/05/2026
 */
public class AnswerDeleteUseCase
    extends AbstractDeleteUseCase<
        AnswerEntity, AnswerDeleteRequest, AnswerDeleteResponse, AnswerFilter> {

  public AnswerDeleteUseCase(
      BaseDao<AnswerEntity, AnswerFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(AnswerDeleteRequest request) {
    AnswerFilter answerFilter = new AnswerFilter();
    answerFilter.setQuestionId(request.getQuestionId());
    answerFilter.setStep(request.getStep() + 1);

    if (super.entityDao.findOne(answerFilter).isPresent()) {
      throw new AppException(AnswerExceptionManager.AnswerError.ONLY_LAST_STEP_CAN_BE_DELETED);
    }
  }

  @Override
  protected void validateRequestWithEntity(
      AnswerDeleteRequest request, AnswerEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(AnswerDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.ANSWER;
  }

  @Override
  protected AnswerFilter prepareFilter(AnswerDeleteRequest answerDeleteRequest) {
    AnswerFilter answerFilter = new AnswerFilter();
    answerFilter.setId(answerDeleteRequest.getId());
    return answerFilter;
  }

  @Override
  protected AnswerDeleteResponse prepareResponse(AnswerEntity entityToUpdate) {
    return new AnswerDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
