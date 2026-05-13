package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.MaterialEntity;
import com.inviq.dao.filter.MaterialFilter;
import com.inviq.dto.tenset.request.MaterialUpdateRequest;
import com.inviq.dto.tenset.response.MaterialUpdateResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class MaterialUpdateUseCase
    extends AbstractUpdateUseCase<
        MaterialEntity, MaterialUpdateRequest, MaterialUpdateResponse, MaterialFilter> {
  public MaterialUpdateUseCase(
      BaseDao<MaterialEntity, MaterialFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(MaterialUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      MaterialUpdateRequest request, MaterialEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(MaterialUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.MATERIAL;
  }

  @Override
  protected MaterialFilter prepareFilterToFindDuplicateRecord(MaterialUpdateRequest request) {
    MaterialFilter materialFilter = new MaterialFilter();
    materialFilter.setMaterialName(request.getMaterialName());
    return materialFilter;
  }

  @Override
  protected MaterialEntity prepareEntityForUpdate(
      MaterialEntity entity, MaterialUpdateRequest request) {
    entity.setMaterial(request.getMaterialName());
    return entity;
  }

  @Override
  protected MaterialUpdateResponse prepareResponse(MaterialEntity entityToUpdate) {
    return new MaterialUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
