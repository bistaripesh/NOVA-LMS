package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.affiliate.entity.LevelSubjectChapterEntity;
import com.inviq.dao.affiliate.filter.LevelSubjectChapterFilter;
import com.inviq.dto.request.LevelSubjectChapterUpdateRequest;
import com.inviq.dto.response.LevelSubjectChapterUpdateResponse;

/**
 * @author ripesh created on 07/05/2026
 */
public class LevelSubjectChapterUpdateUseCase
    extends AbstractUpdateUseCase<
        LevelSubjectChapterEntity,
        LevelSubjectChapterUpdateRequest,
        LevelSubjectChapterUpdateResponse,
        LevelSubjectChapterFilter> {

  public LevelSubjectChapterUpdateUseCase(
      BaseDao<LevelSubjectChapterEntity, LevelSubjectChapterFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(LevelSubjectChapterUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      LevelSubjectChapterUpdateRequest request, LevelSubjectChapterEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(LevelSubjectChapterUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECT_CHAPTERS;
  }

  @Override
  protected LevelSubjectChapterFilter prepareFilterToFindDuplicateRecord(
      LevelSubjectChapterUpdateRequest request) {
    LevelSubjectChapterFilter levelSubjectChapterFilter = new LevelSubjectChapterFilter();
    levelSubjectChapterFilter.setLevelSubjectId(request.getLevelSubjectId());
    levelSubjectChapterFilter.setChapterName(request.getChapterName());
    return levelSubjectChapterFilter;
  }

  @Override
  protected LevelSubjectChapterEntity prepareEntityForUpdate(
      LevelSubjectChapterEntity entity, LevelSubjectChapterUpdateRequest request) {
    entity.setChapter(request.getChapterName());
    entity.setChapterNumber(request.getChapterNumber());
    entity.setDescription(request.getChapterDescription());
    return entity;
  }

  @Override
  protected LevelSubjectChapterUpdateResponse prepareResponse(
      LevelSubjectChapterEntity entityToUpdate) {
    return new LevelSubjectChapterUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
