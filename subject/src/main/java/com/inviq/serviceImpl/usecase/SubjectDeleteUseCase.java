package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.entity.SubjectEntity;
import com.inviq.dao.filter.SubjectFilter;
import com.inviq.dto.tenset.request.SubjectDeleteRequest;
import com.inviq.dto.tenset.response.SubjectDeleteResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class SubjectDeleteUseCase
    extends AbstractDeleteUseCase<
        SubjectEntity, SubjectDeleteRequest, SubjectDeleteResponse, SubjectFilter> {

  public SubjectDeleteUseCase(
      BaseDao<SubjectEntity, SubjectFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(SubjectDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      SubjectDeleteRequest request, SubjectEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(SubjectDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.SUBJECT;
  }

  @Override
  protected SubjectFilter prepareFilter(SubjectDeleteRequest subjectDeleteRequest) {
    SubjectFilter subjectFilter = new SubjectFilter();
    subjectFilter.setId(subjectDeleteRequest.getId());
    return subjectFilter;
  }

  @Override
  protected SubjectDeleteResponse prepareResponse(SubjectEntity entityToUpdate) {
    return new SubjectDeleteResponse();
  }
}
