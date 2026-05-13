package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.affiliate.entity.LevelSubjectChapterEntity;
import com.inviq.dao.affiliate.filter.LevelSubjectChapterFilter;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dto.request.LevelSubjectChapterAddRequest;
import com.inviq.dto.response.LevelSubjectChapterAddResponse;

/**
 * @author ripesh created on 07/05/2026
 */
public class LevelSubjectChapterAddUseCase
    extends AbstractAddUseCase<
        LevelSubjectChapterEntity,
        LevelSubjectChapterAddRequest,
        LevelSubjectChapterAddResponse,
        LevelSubjectChapterFilter> {
  public LevelSubjectChapterAddUseCase(
      BaseDao<LevelSubjectChapterEntity, LevelSubjectChapterFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(LevelSubjectChapterAddRequest request) {}

  @Override
  protected void preValidateRequest(LevelSubjectChapterAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECT_CHAPTERS;
  }

  @Override
  protected LevelSubjectChapterFilter prepareFilterToFindDuplicateRecord(
      LevelSubjectChapterAddRequest request) {
    LevelSubjectChapterFilter levelSubjectChapterFilter = new LevelSubjectChapterFilter();
    levelSubjectChapterFilter.setLevelSubjectId(request.getLevelSubjectId());
    levelSubjectChapterFilter.setChapterName(request.getChapterName());
    return levelSubjectChapterFilter;
  }

  @Override
  protected LevelSubjectChapterEntity prepareEntity(LevelSubjectChapterAddRequest request) {
    LevelSubjectChapterEntity levelSubjectChapterEntity = new LevelSubjectChapterEntity();
    levelSubjectChapterEntity.setChapterNumber(request.getChapterNumber());
    levelSubjectChapterEntity.setLevelSubject(
        new LevelSubjectsEntity() {
          {
            setId(request.getLevelSubjectId());
          }
        });
    levelSubjectChapterEntity.setChapter(request.getChapterName());
    levelSubjectChapterEntity.setDescription(request.getChapterDescription());
    return levelSubjectChapterEntity;
  }

  @Override
  protected LevelSubjectChapterAddResponse prepareResponse(LevelSubjectChapterEntity addedEntity) {
    return new LevelSubjectChapterAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
