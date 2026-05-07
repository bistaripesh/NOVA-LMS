package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.entity.LevelEntity;
import com.inviq.dao.filter.LevelFilter;
import com.inviq.dto.tenset.request.LevelAddRequest;
import com.inviq.dto.tenset.response.LevelAddResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelAddUseCase
    extends AbstractAddUseCase<LevelEntity, LevelAddRequest, LevelAddResponse, LevelFilter> {

  public LevelAddUseCase(
      BaseDao<LevelEntity, LevelFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(LevelAddRequest request) {}

  @Override
  protected void preValidateRequest(LevelAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL;
  }

  @Override
  protected LevelFilter prepareFilterToFindDuplicateRecord(LevelAddRequest request) {
    LevelFilter levelFilter = new LevelFilter();
    levelFilter.setLevelName(request.getLevelName());
    return levelFilter;
  }

  @Override
  protected LevelEntity prepareEntity(LevelAddRequest request) {
    LevelEntity levelEntity = new LevelEntity();
    levelEntity.setLevel(request.getLevelName());
    return levelEntity;
  }

  @Override
  protected LevelAddResponse prepareResponse(LevelEntity addedEntity) {
    return new LevelAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
