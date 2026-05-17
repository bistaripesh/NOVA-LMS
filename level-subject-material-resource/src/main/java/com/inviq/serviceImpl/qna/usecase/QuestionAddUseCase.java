package com.inviq.serviceImpl.qna.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constant.QuestionConstant;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.core.util.HtmlImageProcessor;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dao.qna.question.entity.QuestionEntity;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import com.inviq.dto.qna.question.request.QuestionAddRequest;
import com.inviq.dto.qna.question.response.QuestionAddResponse;

/**
 * @author ripesh created on 17/05/2026
 */
public class QuestionAddUseCase
    extends AbstractAddUseCase<
        QuestionEntity, QuestionAddRequest, QuestionAddResponse, QuestionFilter> {

  public QuestionAddUseCase(
      BaseDao<QuestionEntity, QuestionFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(QuestionAddRequest request) {
    if (StringUtil.isBlankOrNull(request.getQuestion())) return;
    String processedHtml =
        HtmlImageProcessor.processHtml(
            request.getQuestion(), QuestionConstant.QUESTION_FILE_LOCATION);
    request.setQuestion(processedHtml);
  }

  @Override
  protected void preValidateRequest(QuestionAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.QUESTION;
  }

  @Override
  protected QuestionFilter prepareFilterToFindDuplicateRecord(QuestionAddRequest request) {
    return new QuestionFilter();
  }

  @Override
  protected QuestionEntity prepareEntity(QuestionAddRequest request) {
    QuestionEntity entity = new QuestionEntity();
    entity.setQuestion(request.getQuestion());
    entity.setYear(request.getYear());
    entity.setIsDraft(request.getIsDraft());
    entity.setLevelSubjectMaterial(new LevelSubjectMaterialEntity(){{
      setId(request.getLevelSubjectMaterialId());
    }});
    return entity;
  }

  @Override
  protected QuestionAddResponse prepareResponse(QuestionEntity addedEntity) {
    return new QuestionAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
