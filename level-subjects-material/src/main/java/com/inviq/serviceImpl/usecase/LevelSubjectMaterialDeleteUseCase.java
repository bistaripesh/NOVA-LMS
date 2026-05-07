package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dao.filter.LevelSubjectMaterialFilter;
import com.inviq.dto.tenset.request.LevelSubjectMaterialDeleteRequest;
import com.inviq.dto.tenset.response.LevelSubjectMaterialDeleteResponse;

/**
 * @author ripesh created on 06/05/2026
 */
public class LevelSubjectMaterialDeleteUseCase
    extends AbstractDeleteUseCase<
        LevelSubjectMaterialEntity,
        LevelSubjectMaterialDeleteRequest,
        LevelSubjectMaterialDeleteResponse,
        LevelSubjectMaterialFilter> {
  public LevelSubjectMaterialDeleteUseCase(
      BaseDao<LevelSubjectMaterialEntity, LevelSubjectMaterialFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(LevelSubjectMaterialDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      LevelSubjectMaterialDeleteRequest request, LevelSubjectMaterialEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(LevelSubjectMaterialDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECT_MATERIALS;
  }

  @Override
  protected LevelSubjectMaterialFilter prepareFilter(
      LevelSubjectMaterialDeleteRequest levelSubjectMaterialDeleteRequest) {
    LevelSubjectMaterialFilter levelSubjectMaterialFilter = new LevelSubjectMaterialFilter();
    levelSubjectMaterialFilter.setId(levelSubjectMaterialDeleteRequest.getId());
    return levelSubjectMaterialFilter;
  }

  @Override
  protected LevelSubjectMaterialDeleteResponse prepareResponse(
      LevelSubjectMaterialEntity entityToUpdate) {
    return new LevelSubjectMaterialDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
