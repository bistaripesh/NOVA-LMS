package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.entity.LevelEntity;
import com.inviq.dao.filter.LevelFilter;
import com.inviq.dto.tenset.request.LevelDeleteRequest;
import com.inviq.dto.tenset.response.LevelDeleteResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelDeleteUseCase
    extends AbstractDeleteUseCase<
        LevelEntity, LevelDeleteRequest, LevelDeleteResponse, LevelFilter> {
  public LevelDeleteUseCase(
      BaseDao<LevelEntity, LevelFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(LevelDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      LevelDeleteRequest request, LevelEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(LevelDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL;
  }

  @Override
  protected LevelFilter prepareFilter(LevelDeleteRequest levelDeleteRequest) {
    LevelFilter levelFilter = new LevelFilter();
    levelFilter.setId(levelDeleteRequest.getId());
    return levelFilter;
  }

  @Override
  protected LevelDeleteResponse prepareResponse(LevelEntity entityToUpdate) {
    return new LevelDeleteResponse();
  }
}
