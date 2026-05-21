package com.inviq.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dao.entity.UserEntity;
import com.inviq.dao.filter.PaymentFilter;
import com.inviq.dto.request.PaymentAddRequest;
import com.inviq.dto.response.PaymentAddResponse;

/**
 * @author ripesh created on 21/05/2026
 */
public class PaymentAddUseCase
    extends AbstractAddUseCase<
        PaymentEntity, PaymentAddRequest, PaymentAddResponse, PaymentFilter> {

  public PaymentAddUseCase(
      BaseDao<PaymentEntity, PaymentFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(PaymentAddRequest request) {}

  @Override
  protected void preValidateRequest(PaymentAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.PAYMENT;
  }

  @Override
  protected PaymentFilter prepareFilterToFindDuplicateRecord(PaymentAddRequest request) {
    PaymentFilter filter = new PaymentFilter();
    filter.setLevelSubjectId(request.getLevelSubjectId());
    filter.setUserId(request.getUserId());
    filter.setIsPaymentSuccess(true);
    return filter;
  }

  @Override
  protected PaymentEntity prepareEntity(PaymentAddRequest request) {
    PaymentEntity entity = new PaymentEntity();
    entity.setLevelSubjects(
        new LevelSubjectsEntity() {
          {
            setId(request.getLevelSubjectId());
          }
        });
    entity.setUser(
        new UserEntity() {
          {
            setId(request.getUserId());
          }
        });
    entity.setAmount(request.getAmount());
    entity.setReferenceLabel(request.getReferenceLabel());
    entity.setReferralCode(request.getReferralCode());
    entity.setIsPaymentSuccess(false);
    return entity;
  }

  @Override
  protected PaymentAddResponse prepareResponse(PaymentEntity addedEntity) {
    return new PaymentAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
