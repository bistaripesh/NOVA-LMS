package com.inviq.serviceImpl.tenset.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dao.tenset.filter.TenSetFilter;
import com.inviq.dto.tenset.request.TenSetDeleteRequest;
import com.inviq.dto.tenset.response.TenSetDeleteResponse;

/**
 * @author ripesh created on 06/05/2026
 */
public class TenSetDeleteUseCase
    extends AbstractDeleteUseCase<
        TenSetEntity, TenSetDeleteRequest, TenSetDeleteResponse, TenSetFilter> {
  public TenSetDeleteUseCase(
      BaseDao<TenSetEntity, TenSetFilter> entityDao, AuditDao auditDao, ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(TenSetDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      TenSetDeleteRequest request, TenSetEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(TenSetDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.TEN_SET_MATERIALS;
  }

  @Override
  protected TenSetFilter prepareFilter(TenSetDeleteRequest tenSetDeleteRequest) {
    TenSetFilter tenSetFilter = new TenSetFilter();
    tenSetFilter.setId(tenSetDeleteRequest.getId());
    return tenSetFilter;
  }

  @Override
  protected TenSetDeleteResponse prepareResponse(TenSetEntity entityToUpdate) {
    return new TenSetDeleteResponse();
  }
}
