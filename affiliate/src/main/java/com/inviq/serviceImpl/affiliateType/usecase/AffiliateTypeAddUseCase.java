package com.inviq.serviceImpl.affiliateType.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import com.inviq.dao.affiliateType.filter.AffiliateTypeFilter;
import com.inviq.dto.affiliateType.request.AffiliateTypeAddRequest;
import com.inviq.dto.affiliateType.response.AffiliateTypeAddResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateTypeAddUseCase
    extends AbstractAddUseCase<
        AffiliateTypeEntity,
        AffiliateTypeAddRequest,
        AffiliateTypeAddResponse,
        AffiliateTypeFilter> {

  public AffiliateTypeAddUseCase(
      BaseDao<AffiliateTypeEntity, AffiliateTypeFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(AffiliateTypeAddRequest request) {}

  @Override
  protected void preValidateRequest(AffiliateTypeAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.AFFILIATE_TYPE;
  }

  @Override
  protected AffiliateTypeFilter prepareFilterToFindDuplicateRecord(
      AffiliateTypeAddRequest request) {
    AffiliateTypeFilter affiliateTypeFilter = new AffiliateTypeFilter();
    affiliateTypeFilter.setAffiliateType(request.getAffiliateType());
    return affiliateTypeFilter;
  }

  @Override
  protected AffiliateTypeEntity prepareEntity(AffiliateTypeAddRequest request) {
    AffiliateTypeEntity affiliateTypeEntity = new AffiliateTypeEntity();
    affiliateTypeEntity.setAffiliateType(request.getAffiliateType());
    return affiliateTypeEntity;
  }

  @Override
  protected AffiliateTypeAddResponse prepareResponse(AffiliateTypeEntity addedEntity) {
    return new AffiliateTypeAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
