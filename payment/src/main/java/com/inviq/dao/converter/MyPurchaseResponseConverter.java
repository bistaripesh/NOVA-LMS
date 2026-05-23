package com.inviq.dao.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dao.entity.PaymentEntity;
import com.inviq.dto.response.MyPurchaseResponse;

/**
 * @author ripesh created on 23/05/2026
 */
public class MyPurchaseResponseConverter implements Converter<PaymentEntity> {
  @Override
  public Response getResponse(PaymentEntity entity) {
    MyPurchaseResponse response = new MyPurchaseResponse();

    response.setPaymentId(entity.getId());
    response.setAmount(entity.getAmount());
    response.setIsPaymentSuccess(entity.getIsPaymentSuccess());
    response.setPurchasedOn(entity.getCreatedOn());

    LevelSubjectsEntity levelSubjects = entity.getLevelSubjects();
    response.setLevelSubjectId(levelSubjects.getId());
    response.setLevel(levelSubjects.getLevel().getLevel());
    response.setSubject(levelSubjects.getSubject().getSubject());

    return response;
  }
}
