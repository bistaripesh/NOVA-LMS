package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.entity.SubjectEntity;
import com.inviq.dao.filter.SubjectFilter;
import com.inviq.dto.tenset.request.SubjectAddRequest;
import com.inviq.dto.tenset.response.SubjectAddResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class SubjectAddUseCase
    extends AbstractAddUseCase<
        SubjectEntity, SubjectAddRequest, SubjectAddResponse, SubjectFilter> {
  public SubjectAddUseCase(
      BaseDao<SubjectEntity, SubjectFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(SubjectAddRequest request) {}

  @Override
  protected void preValidateRequest(SubjectAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.SUBJECT;
  }

  @Override
  protected SubjectFilter prepareFilterToFindDuplicateRecord(SubjectAddRequest request) {
    SubjectFilter subjectFilter = new SubjectFilter();
    subjectFilter.setSubjectName(request.getSubjectName());
    return subjectFilter;
  }

  @Override
  protected SubjectEntity prepareEntity(SubjectAddRequest request) {
    SubjectEntity subjectEntity = new SubjectEntity();
    subjectEntity.setSubject(request.getSubjectName());
    return subjectEntity;
  }

  @Override
  protected SubjectAddResponse prepareResponse(SubjectEntity addedEntity) {
    return new SubjectAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
