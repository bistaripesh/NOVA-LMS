package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.entity.MaterialEntity;
import com.inviq.dao.filter.MaterialFilter;
import com.inviq.dto.tenset.request.MaterialDeleteRequest;
import com.inviq.dto.tenset.response.MaterialDeleteResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class MaterialDeleteUseCase
    extends AbstractDeleteUseCase<
        MaterialEntity, MaterialDeleteRequest, MaterialDeleteResponse, MaterialFilter> {
  public MaterialDeleteUseCase(
      BaseDao<MaterialEntity, MaterialFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(MaterialDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      MaterialDeleteRequest request, MaterialEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(MaterialDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.MATERIAL;
  }

  @Override
  protected MaterialFilter prepareFilter(MaterialDeleteRequest materialDeleteRequest) {
    MaterialFilter materialFilter = new MaterialFilter();
    materialFilter.setId(materialDeleteRequest.getId());
    return materialFilter;
  }

  @Override
  protected MaterialDeleteResponse prepareResponse(MaterialEntity entityToUpdate) {
    return new MaterialDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
