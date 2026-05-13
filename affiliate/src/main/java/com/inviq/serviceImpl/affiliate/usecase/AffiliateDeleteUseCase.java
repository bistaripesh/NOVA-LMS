package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractDeleteUseCase;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
import com.inviq.dto.affiliate.request.AffiliateDeleteRequest;
import com.inviq.dto.affiliate.response.AffiliateDeleteResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateDeleteUseCase
    extends AbstractDeleteUseCase<
        AffiliateEntity, AffiliateDeleteRequest, AffiliateDeleteResponse, AffiliateFilter> {

  public AffiliateDeleteUseCase(
      BaseDao<AffiliateEntity, AffiliateFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(AffiliateDeleteRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      AffiliateDeleteRequest request, AffiliateEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(AffiliateDeleteRequest request) {}

  @Override
  protected Module getModule() {
    return Module.AFFILIATE;
  }

  @Override
  protected AffiliateFilter prepareFilter(AffiliateDeleteRequest affiliateDeleteRequest) {
    AffiliateFilter affiliateFilter = new AffiliateFilter();
    affiliateFilter.setId(affiliateDeleteRequest.getId());
    return affiliateFilter;
  }

  @Override
  protected AffiliateDeleteResponse prepareResponse(AffiliateEntity entityToUpdate) {
    return new AffiliateDeleteResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
