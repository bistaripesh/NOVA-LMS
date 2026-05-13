package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.util.ReferralCodeUtil;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import com.inviq.dao.entity.AffiliateReferralEntity;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dto.request.AffiliateReferralAddRequest;
import com.inviq.dto.response.AffiliateReferralAddResponse;

/**
 * @author ripesh created on 13/05/2026
 */
public class AffiliateReferralAddUseCase
    extends AbstractAddUseCase<
        AffiliateReferralEntity,
        AffiliateReferralAddRequest,
        AffiliateReferralAddResponse,
        AffiliateReferralFilter> {

  public AffiliateReferralAddUseCase(
      BaseDao<AffiliateReferralEntity, AffiliateReferralFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(AffiliateReferralAddRequest request) {}

  @Override
  protected void preValidateRequest(AffiliateReferralAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.AFFILIATE_REFERRAL;
  }

  @Override
  protected AffiliateReferralFilter prepareFilterToFindDuplicateRecord(
      AffiliateReferralAddRequest request) {
    AffiliateReferralFilter affiliateReferralFilter = new AffiliateReferralFilter();
    affiliateReferralFilter.setAffiliateId(request.getAffiliateId());
    affiliateReferralFilter.setIsReferralCodeActive(true);
    return affiliateReferralFilter;
  }

  @Override
  protected AffiliateReferralEntity prepareEntity(AffiliateReferralAddRequest request) {
    AffiliateReferralEntity entity = new AffiliateReferralEntity();
    entity.setAffiliate(
        new AffiliateEntity() {
          {
            setId(request.getAffiliateId());
          }
        });
    entity.setCommissionPercent(request.getCommissionPercent());
    entity.setReferralCode(ReferralCodeUtil.generateReferralCode());
    entity.setDescription(request.getDescription());
    entity.setIsReferralCodeActive(true);
    return entity;
  }

  @Override
  protected AffiliateReferralAddResponse prepareResponse(AffiliateReferralEntity addedEntity) {
    return new AffiliateReferralAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
