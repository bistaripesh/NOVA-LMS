package com.inviq.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.request.PaymentUpdateRequest;
import com.inviq.dto.response.PaymentUpdateResponse;

/**
 * @author ripesh created on 21/05/2026
 */
public class PaymentUpdateUseCase
    extends AbstractUpdateUseCase<
        PaymentEntity, PaymentUpdateRequest, PaymentUpdateResponse, PaymentFilter> {

  public PaymentUpdateUseCase(
      BaseDao<PaymentEntity, PaymentFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(PaymentUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      PaymentUpdateRequest request, PaymentEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(PaymentUpdateRequest request) {}

  @Override
  protected Module getModule() {
    return Module.PAYMENT;
  }

  @Override
  protected PaymentFilter prepareFilterToFindDuplicateRecord(PaymentUpdateRequest request) {
    PaymentFilter filter = new PaymentFilter();
    filter.setIsPaymentSuccess(request.getIsPaymentSuccess());
    filter.setLevelSubjectId(request.getLevelSubjectId());
    filter.setUserId(request.getUserId());
    return filter;
  }

  @Override
  protected PaymentEntity prepareEntityForUpdate(
      PaymentEntity entity, PaymentUpdateRequest request) {
    entity.setIsPaymentSuccess(request.getIsPaymentSuccess());
    return entity;
  }

  @Override
  protected PaymentUpdateResponse prepareResponse(PaymentEntity entityToUpdate) {
    return new PaymentUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
