package com.inviq.serviceImpl.qna.answer.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constant.AnswerConstant;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.core.util.HtmlImageProcessor;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.qna.answer.entity.AnswerEntity;
import com.inviq.dao.qna.answer.filter.AnswerFilter;
import com.inviq.dao.qna.question.entity.QuestionEntity;
import com.inviq.dto.qna.answer.request.AnswerAddRequest;
import com.inviq.dto.qna.answer.response.AnswerAddResponse;

/**
 * @author ripesh created on 17/05/2026
 */
public class AnswerAddUseCase
    extends AbstractAddUseCase<AnswerEntity, AnswerAddRequest, AnswerAddResponse, AnswerFilter> {

  public AnswerAddUseCase(
      BaseDao<AnswerEntity, AnswerFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(AnswerAddRequest request) {
    if (StringUtil.isBlankOrNull(request.getAnswer())) return;
    String processedHtml =
        HtmlImageProcessor.processHtml(request.getAnswer(), AnswerConstant.ANSWER_FILE_LOCATION);
    request.setAnswer(processedHtml);
  }

  @Override
  protected void preValidateRequest(AnswerAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.ANSWER;
  }

  @Override
  protected AnswerFilter prepareFilterToFindDuplicateRecord(AnswerAddRequest request) {
    AnswerFilter answerFilter = new AnswerFilter();
    answerFilter.setQuestionId(request.getQuestionId());
    answerFilter.setStep(request.getStep());
    return answerFilter;
  }

  @Override
  protected AnswerEntity prepareEntity(AnswerAddRequest request) {
    AnswerEntity answerEntity = new AnswerEntity();
    answerEntity.setAnswer(request.getAnswer());
    answerEntity.setStep(request.getStep());
    answerEntity.setQuestion(
        new QuestionEntity() {
          {
            setId(request.getQuestionId());
          }
        });
    return answerEntity;
  }

  @Override
  protected AnswerAddResponse prepareResponse(AnswerEntity addedEntity) {
    return new AnswerAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
