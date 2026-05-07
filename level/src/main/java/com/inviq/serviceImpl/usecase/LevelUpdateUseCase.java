package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.LevelEntity;
import com.inviq.dao.filter.LevelFilter;
import com.inviq.dto.tenset.request.LevelUpdateRequest;
import com.inviq.dto.tenset.response.LevelUpdateResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelUpdateUseCase
    extends AbstractUpdateUseCase<
        LevelEntity, LevelUpdateRequest, LevelUpdateResponse, LevelFilter> {

  public LevelUpdateUseCase(
      BaseDao<LevelEntity, LevelFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(LevelUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      LevelUpdateRequest request, LevelEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(LevelUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL;
  }

  @Override
  protected LevelFilter prepareFilterToFindDuplicateRecord(LevelUpdateRequest request) {
    LevelFilter levelFilter = new LevelFilter();
    levelFilter.setLevelName(request.getLevelName());
    return levelFilter;
  }

  @Override
  protected LevelEntity prepareEntityForUpdate(LevelEntity entity, LevelUpdateRequest request) {
    entity.setLevel(request.getLevelName());
    return entity;
  }

  @Override
  protected LevelUpdateResponse prepareResponse(LevelEntity entityToUpdate) {
    return new LevelUpdateResponse();
  }
}
