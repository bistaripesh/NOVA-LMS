package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.entity.MaterialEntity;
import com.inviq.dao.filter.LevelSubjectMaterialFilter;
import com.inviq.dto.tenset.request.LevelSubjectMaterialAddRequest;
import com.inviq.dto.tenset.response.LevelSubjectMaterialAddResponse;

/**
 * @author ripesh created on 06/05/2026
 */
public class LevelSubjectMaterialAddUseCase
    extends AbstractAddUseCase<
        LevelSubjectMaterialEntity,
        LevelSubjectMaterialAddRequest,
        LevelSubjectMaterialAddResponse,
        LevelSubjectMaterialFilter> {
  public LevelSubjectMaterialAddUseCase(
      BaseDao<LevelSubjectMaterialEntity, LevelSubjectMaterialFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(LevelSubjectMaterialAddRequest request) {}

  @Override
  protected void preValidateRequest(LevelSubjectMaterialAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECT_MATERIALS;
  }

  @Override
  protected LevelSubjectMaterialFilter prepareFilterToFindDuplicateRecord(
      LevelSubjectMaterialAddRequest request) {
    LevelSubjectMaterialFilter levelSubjectMaterialFilter = new LevelSubjectMaterialFilter();
    levelSubjectMaterialFilter.setLevelSubjectId(request.getLevelSubjectId());
    levelSubjectMaterialFilter.setMaterialId(request.getMaterialId());
    return levelSubjectMaterialFilter;
  }

  @Override
  protected LevelSubjectMaterialEntity prepareEntity(LevelSubjectMaterialAddRequest request) {
    LevelSubjectMaterialEntity levelSubjectMaterialEntity = new LevelSubjectMaterialEntity();
    levelSubjectMaterialEntity.setLevelSubject(
        new LevelSubjectsEntity() {
          {
            setId(request.getLevelSubjectId());
          }
        });
    levelSubjectMaterialEntity.setMaterial(
        new MaterialEntity() {
          {
            setId(request.getMaterialId());
          }
        });
    return levelSubjectMaterialEntity;
  }

  @Override
  protected LevelSubjectMaterialAddResponse prepareResponse(
      LevelSubjectMaterialEntity addedEntity) {
    return new LevelSubjectMaterialAddResponse();
  }
}
