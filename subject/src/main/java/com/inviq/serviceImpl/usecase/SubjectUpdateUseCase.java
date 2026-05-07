package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.SubjectEntity;
import com.inviq.dao.filter.SubjectFilter;
import com.inviq.dto.tenset.request.SubjectUpdateRequest;
import com.inviq.dto.tenset.response.SubjectUpdateResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class SubjectUpdateUseCase
    extends AbstractUpdateUseCase<
        SubjectEntity, SubjectUpdateRequest, SubjectUpdateResponse, SubjectFilter> {

  public SubjectUpdateUseCase(
      BaseDao<SubjectEntity, SubjectFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(SubjectUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      SubjectUpdateRequest request, SubjectEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(SubjectUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.SUBJECT;
  }

  @Override
  protected SubjectFilter prepareFilterToFindDuplicateRecord(SubjectUpdateRequest request) {
    SubjectFilter subjectFilter = new SubjectFilter();
    subjectFilter.setSubjectName(request.getSubjectName());
    return subjectFilter;
  }

  @Override
  protected SubjectEntity prepareEntityForUpdate(
      SubjectEntity entity, SubjectUpdateRequest request) {
    entity.setSubject(request.getSubjectName());
    return entity;
  }

  @Override
  protected SubjectUpdateResponse prepareResponse(SubjectEntity entityToUpdate) {
    return new SubjectUpdateResponse();
  }
}
