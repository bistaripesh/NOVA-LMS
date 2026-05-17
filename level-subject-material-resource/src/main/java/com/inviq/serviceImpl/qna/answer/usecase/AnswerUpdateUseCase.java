package com.inviq.serviceImpl.qna.answer.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constant.AnswerConstant;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.core.util.HtmlImageProcessor;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.qna.answer.entity.AnswerEntity;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
import com.inviq.dto.qna.answer.request.AnswerUpdateRequest;
import com.inviq.dto.qna.answer.response.AnswerUpdateResponse;

/**
 * @author ripesh created on 17/05/2026
 */
public class AnswerUpdateUseCase
    extends AbstractUpdateUseCase<
        AnswerEntity, AnswerUpdateRequest, AnswerUpdateResponse, AnswerFilter> {

  public AnswerUpdateUseCase(
      BaseDao<AnswerEntity, AnswerFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(AnswerUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      AnswerUpdateRequest request, AnswerEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(AnswerUpdateRequest request) {
    if (StringUtil.isBlankOrNull(request.getAnswer())) return;
    String processedHtml =
        HtmlImageProcessor.processHtml(request.getAnswer(), AnswerConstant.ANSWER_FILE_LOCATION);
    request.setAnswer(processedHtml);
  }

  @Override
  protected Module getModule() {
    return Module.ANSWER;
  }

  @Override
  protected AnswerFilter prepareFilterToFindDuplicateRecord(AnswerUpdateRequest request) {
    AnswerFilter answerFilter = new AnswerFilter();
    answerFilter.setStep(request.getStep());
    answerFilter.setQuestionId(request.getQuestionId());
    return answerFilter;
  }

  @Override
  protected AnswerEntity prepareEntityForUpdate(AnswerEntity entity, AnswerUpdateRequest request) {
    entity.setAnswer(request.getAnswer());
    entity.setStep(request.getStep());
    return entity;
  }

  @Override
  protected AnswerUpdateResponse prepareResponse(AnswerEntity entityToUpdate) {
    return new AnswerUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
