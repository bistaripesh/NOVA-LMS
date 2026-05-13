package com.inviq.serviceImpl.affiliateType.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import com.inviq.dao.affiliateType.filter.AffiliateTypeFilter;
import com.inviq.dto.affiliateType.request.AffiliateTypeUpdateRequest;
import com.inviq.dto.affiliateType.response.AffiliateTypeUpdateResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateTypeUpdateUseCase
    extends AbstractUpdateUseCase<
        AffiliateTypeEntity,
        AffiliateTypeUpdateRequest,
        AffiliateTypeUpdateResponse,
        AffiliateTypeFilter> {

  public AffiliateTypeUpdateUseCase(
      BaseDao<AffiliateTypeEntity, AffiliateTypeFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(AffiliateTypeUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      AffiliateTypeUpdateRequest request, AffiliateTypeEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(AffiliateTypeUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.AFFILIATE_TYPE;
  }

  @Override
  protected AffiliateTypeFilter prepareFilterToFindDuplicateRecord(
      AffiliateTypeUpdateRequest request) {
    AffiliateTypeFilter affiliateTypeFilter = new AffiliateTypeFilter();
    affiliateTypeFilter.setAffiliateType(request.getAffiliateType());
    return affiliateTypeFilter;
  }

  @Override
  protected AffiliateTypeEntity prepareEntityForUpdate(
      AffiliateTypeEntity entity, AffiliateTypeUpdateRequest request) {
    entity.setAffiliateType(request.getAffiliateType());
    return entity;
  }

  @Override
  protected AffiliateTypeUpdateResponse prepareResponse(AffiliateTypeEntity entityToUpdate) {
    return new AffiliateTypeUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
