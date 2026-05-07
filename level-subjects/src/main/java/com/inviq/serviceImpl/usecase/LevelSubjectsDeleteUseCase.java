package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.tenset.request.LevelSubjectsDeleteRequest;
import com.inviq.dto.tenset.response.LevelSubjectsDeleteResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelSubjectsDeleteUseCase
    extends AbstractDeleteUseCase<
        LevelSubjectsEntity,
        LevelSubjectsDeleteRequest,
        LevelSubjectsDeleteResponse,
        LevelSubjectsFilter> {

  public LevelSubjectsDeleteUseCase(
      BaseDao<LevelSubjectsEntity, LevelSubjectsFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(LevelSubjectsDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      LevelSubjectsDeleteRequest request, LevelSubjectsEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(LevelSubjectsDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECTS;
  }

  @Override
  protected LevelSubjectsFilter prepareFilter(
      LevelSubjectsDeleteRequest levelSubjectsDeleteRequest) {
    LevelSubjectsFilter levelSubjectsFilter = new LevelSubjectsFilter();
    levelSubjectsFilter.setId(levelSubjectsDeleteRequest.getId());
    return levelSubjectsFilter;
  }

  @Override
  protected LevelSubjectsDeleteResponse prepareResponse(LevelSubjectsEntity entityToUpdate) {
    return new LevelSubjectsDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
