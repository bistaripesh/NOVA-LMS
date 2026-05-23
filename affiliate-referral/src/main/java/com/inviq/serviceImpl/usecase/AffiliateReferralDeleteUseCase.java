package com.inviq.serviceImpl.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.entity.exception.DatabaseExceptionManager;
import com.inviq.core.exception.AppException;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.AffiliateReferralEntity;
import com.inviq.dao.filter.AffiliateReferralFilter;
import com.inviq.dto.request.AffiliateReferralUpdateRequest;
import com.inviq.dto.response.AffiliateReferralUpdateResponse;

/**
 * @author ripesh created on 13/05/2026
 */
public class AffiliateReferralDeleteUseCase
    extends AbstractUpdateUseCase<
        AffiliateReferralEntity,
        AffiliateReferralUpdateRequest,
        AffiliateReferralUpdateResponse,
        AffiliateReferralFilter> {

  public AffiliateReferralDeleteUseCase(
      BaseDao<AffiliateReferralEntity, AffiliateReferralFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(AffiliateReferralUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      AffiliateReferralUpdateRequest request, AffiliateReferralEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(AffiliateReferralUpdateRequest request) {
    AffiliateReferralFilter filter = new AffiliateReferralFilter();
    filter.setId(request.getId());
    AffiliateReferralEntity referralEntity =
        this.entityDao
            .findOne(filter)
            .orElseThrow(
                () -> new AppException(DatabaseExceptionManager.DatabaseError.RECORD_NOT_FOUND));
    request.setIsTransactionSettled(referralEntity.getIsTransactionSettled());
  }

  @Override
  protected Module getModule() {
    return Module.AFFILIATE_REFERRAL;
  }

  @Override
  protected AffiliateReferralFilter prepareFilterToFindDuplicateRecord(
      AffiliateReferralUpdateRequest request) {
    return new AffiliateReferralFilter() {
      {
        setId(request.getId());
        setIsReferralCodeActive(false);
      }
    };
  }

  @Override
  protected AffiliateReferralEntity prepareEntityForUpdate(
      AffiliateReferralEntity entity, AffiliateReferralUpdateRequest request) {
    entity.setIsReferralCodeActive(false);
    return entity;
  }

  @Override
  protected AffiliateReferralUpdateResponse prepareResponse(
      AffiliateReferralEntity entityToUpdate) {
    return new AffiliateReferralUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
