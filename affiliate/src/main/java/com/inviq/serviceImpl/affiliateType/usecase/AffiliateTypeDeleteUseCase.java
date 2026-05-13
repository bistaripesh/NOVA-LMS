package com.inviq.serviceImpl.affiliateType.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import com.inviq.dao.affiliateType.filter.AffiliateTypeFilter;
import com.inviq.dto.affiliateType.request.AffiliateTypeDeleteRequest;
import com.inviq.dto.affiliateType.response.AffiliateTypeDeleteResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateTypeDeleteUseCase
    extends AbstractDeleteUseCase<
        AffiliateTypeEntity,
        AffiliateTypeDeleteRequest,
        AffiliateTypeDeleteResponse,
        AffiliateTypeFilter> {

  public AffiliateTypeDeleteUseCase(
      BaseDao<AffiliateTypeEntity, AffiliateTypeFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(AffiliateTypeDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      AffiliateTypeDeleteRequest request, AffiliateTypeEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(AffiliateTypeDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.AFFILIATE_TYPE;
  }

  @Override
  protected AffiliateTypeFilter prepareFilter(
      AffiliateTypeDeleteRequest affiliateTypeDeleteRequest) {
    AffiliateTypeFilter affiliateTypeFilter = new AffiliateTypeFilter();
    affiliateTypeFilter.setId(affiliateTypeDeleteRequest.getId());
    return affiliateTypeFilter;
  }

  @Override
  protected AffiliateTypeDeleteResponse prepareResponse(AffiliateTypeEntity entityToUpdate) {
    return new AffiliateTypeDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
