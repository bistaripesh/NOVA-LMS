package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.affiliate.entity.LevelSubjectChapterEntity;
import com.inviq.dao.affiliate.filter.LevelSubjectChapterFilter;
import com.inviq.dto.request.LevelSubjectChapterDeleteRequest;
import com.inviq.dto.response.LevelSubjectChapterDeleteResponse;

/**
 * @author ripesh created on 07/05/2026
 */
public class LevelSubjectChapterDeleteUseCase
    extends AbstractDeleteUseCase<
        LevelSubjectChapterEntity,
        LevelSubjectChapterDeleteRequest,
        LevelSubjectChapterDeleteResponse,
        LevelSubjectChapterFilter> {

  public LevelSubjectChapterDeleteUseCase(
      BaseDao<LevelSubjectChapterEntity, LevelSubjectChapterFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(LevelSubjectChapterDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      LevelSubjectChapterDeleteRequest request, LevelSubjectChapterEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(LevelSubjectChapterDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECT_CHAPTERS;
  }

  @Override
  protected LevelSubjectChapterFilter prepareFilter(
      LevelSubjectChapterDeleteRequest levelSubjectChapterDeleteRequest) {
    LevelSubjectChapterFilter levelSubjectChapterFilter = new LevelSubjectChapterFilter();
    levelSubjectChapterFilter.setId(levelSubjectChapterDeleteRequest.getId());
    return levelSubjectChapterFilter;
  }

  @Override
  protected LevelSubjectChapterDeleteResponse prepareResponse(
      LevelSubjectChapterEntity entityToUpdate) {
    return new LevelSubjectChapterDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
