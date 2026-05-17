package com.inviq.serviceImpl.qna.question.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constant.QuestionConstant;
import com.inviq.core.context.UserPrincipal;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.core.user.UserType;
import com.inviq.core.util.HtmlImageProcessor;
import com.inviq.core.util.StringUtil;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dao.qna.question.entity.QuestionEntity;
import com.inviq.dao.qna.question.filter.QuestionFilter;
import com.inviq.dto.qna.question.request.QuestionUpdateRequest;
import com.inviq.dto.qna.question.response.QuestionUpdateResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ripesh created on 17/05/2026
 */
public class QuestionUpdateUseCase
    extends AbstractUpdateUseCase<
        QuestionEntity, QuestionUpdateRequest, QuestionUpdateResponse, QuestionFilter> {
  public QuestionUpdateUseCase(
      BaseDao<QuestionEntity, QuestionFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(QuestionUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      QuestionUpdateRequest request, QuestionEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(QuestionUpdateRequest request) {
    if (StringUtil.isBlankOrNull(request.getQuestion())) return;
    UserPrincipal principal =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal.getUserType() == UserType.ADMIN) {
      request.setIsDraft(false);
    }
    if (principal.getUserType() == UserType.WRITER) {
      request.setIsDraft(true);
    }
    String processedHtml =
        HtmlImageProcessor.processHtml(
            request.getQuestion(), QuestionConstant.QUESTION_FILE_LOCATION);
    request.setQuestion(processedHtml);
  }

  @Override
  protected Module getModule() {
    return Module.QUESTION;
  }

  @Override
  protected QuestionFilter prepareFilterToFindDuplicateRecord(QuestionUpdateRequest request) {
    return new QuestionFilter();
  }

  @Override
  protected QuestionEntity prepareEntityForUpdate(
      QuestionEntity entity, QuestionUpdateRequest request) {
    entity.setQuestion(request.getQuestion());
    entity.setYear(request.getYear());
    entity.setIsDraft(request.getIsDraft());
    entity.setLevelSubjectMaterial(
        new LevelSubjectMaterialEntity() {
          {
            setId(request.getLevelSubjectMaterialId());
          }
        });
    return entity;
  }

  @Override
  protected QuestionUpdateResponse prepareResponse(QuestionEntity entityToUpdate) {
    return new QuestionUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
