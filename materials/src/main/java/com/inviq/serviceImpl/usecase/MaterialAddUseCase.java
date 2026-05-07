package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.entity.MaterialEntity;
import com.inviq.dao.filter.MaterialFilter;
import com.inviq.dto.tenset.request.MaterialAddRequest;
import com.inviq.dto.tenset.response.MaterialAddResponse;

/**
 * @author ripesh created on 05/05/2026
 */
public class MaterialAddUseCase
    extends AbstractAddUseCase<
        MaterialEntity, MaterialAddRequest, MaterialAddResponse, MaterialFilter> {
  public MaterialAddUseCase(
      BaseDao<MaterialEntity, MaterialFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(MaterialAddRequest request) {}

  @Override
  protected void preValidateRequest(MaterialAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.MATERIAL;
  }

  @Override
  protected MaterialFilter prepareFilterToFindDuplicateRecord(MaterialAddRequest request) {
    MaterialFilter materialFilter = new MaterialFilter();
    materialFilter.setMaterialName(request.getMaterialName());
    return materialFilter;
  }

  @Override
  protected MaterialEntity prepareEntity(MaterialAddRequest request) {
    MaterialEntity materialEntity = new MaterialEntity();
    materialEntity.setMaterial(request.getMaterialName());
    return materialEntity;
  }

  @Override
  protected MaterialAddResponse prepareResponse(MaterialEntity addedEntity) {
    return new MaterialAddResponse();
  }
}
