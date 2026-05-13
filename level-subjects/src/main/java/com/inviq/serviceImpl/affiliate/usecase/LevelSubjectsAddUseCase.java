package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.entity.LevelEntity;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.entity.SubjectEntity;
import com.inviq.dao.filter.LevelSubjectsFilter;
import com.inviq.dto.tenset.request.LevelSubjectsAddRequest;
import com.inviq.dto.tenset.response.LevelSubjectsAddResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class LevelSubjectsAddUseCase
    extends AbstractAddUseCase<
        LevelSubjectsEntity,
        LevelSubjectsAddRequest,
        LevelSubjectsAddResponse,
        LevelSubjectsFilter> {

  public LevelSubjectsAddUseCase(
      BaseDao<LevelSubjectsEntity, LevelSubjectsFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(LevelSubjectsAddRequest request) {}

  @Override
  protected void preValidateRequest(LevelSubjectsAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.LEVEL_SUBJECTS;
  }

  @Override
  protected LevelSubjectsFilter prepareFilterToFindDuplicateRecord(
      LevelSubjectsAddRequest request) {
    LevelSubjectsFilter levelSubjectsFilter = new LevelSubjectsFilter();
    levelSubjectsFilter.setLevelId(request.getLevelId());
    levelSubjectsFilter.setSubjectId(request.getSubjectId());
    return levelSubjectsFilter;
  }

  @Override
  protected LevelSubjectsEntity prepareEntity(LevelSubjectsAddRequest request) {
    LevelSubjectsEntity levelSubjectsEntity = new LevelSubjectsEntity();
    levelSubjectsEntity.setLevel(
        new LevelEntity() {
          {
            setId(request.getLevelId());
          }
        });

    levelSubjectsEntity.setSubject(
        new SubjectEntity() {
          {
            setId(request.getSubjectId());
          }
        });
    return levelSubjectsEntity;
  }

  @Override
  protected LevelSubjectsAddResponse prepareResponse(LevelSubjectsEntity addedEntity) {
    return new LevelSubjectsAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
