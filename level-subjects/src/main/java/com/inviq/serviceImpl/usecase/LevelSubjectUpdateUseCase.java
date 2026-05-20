package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.request.LevelSubjectsUpdateRequest;
import com.inviq.dto.response.LevelSubjectsUpdateResponse;

/**
 * @author ripesh created on 19/05/2026
 */
public class LevelSubjectUpdateUseCase
    extends AbstractUpdateUseCase<
        LevelSubjectsEntity,
        LevelSubjectsUpdateRequest,
        LevelSubjectsUpdateResponse,
        LevelSubjectsFilter> {
  public LevelSubjectUpdateUseCase(
      BaseDao<LevelSubjectsEntity, LevelSubjectsFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(LevelSubjectsUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      LevelSubjectsUpdateRequest request, LevelSubjectsEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(LevelSubjectsUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECTS;
  }

  @Override
  protected LevelSubjectsFilter prepareFilterToFindDuplicateRecord(
      LevelSubjectsUpdateRequest request) {
    LevelSubjectsFilter filter = new LevelSubjectsFilter();
    filter.setSubjectId(request.getSubjectId());
    filter.setLevelId(request.getLevelId());
    filter.setPrice(request.getPrice());
    return filter;
  }

  @Override
  protected LevelSubjectsEntity prepareEntityForUpdate(
      LevelSubjectsEntity entity, LevelSubjectsUpdateRequest request) {
    entity.setPrice(request.getPrice());
    return entity;
  }

  @Override
  protected LevelSubjectsUpdateResponse prepareResponse(LevelSubjectsEntity entityToUpdate) {
    return new LevelSubjectsUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
