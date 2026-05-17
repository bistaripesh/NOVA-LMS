package com.inviq.dao.qna.answer.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.core.util.HtmlImageProcessor;
import com.inviq.dao.qna.answer.entity.AnswerEntity;
import com.inviq.dto.qna.answer.response.AnswerSearchResponse;

/**
 * @author ripesh created on 17/05/2026
 */
public class AnswerEntityConverter implements Converter<AnswerEntity> {
  @Override
  public Response getResponse(AnswerEntity entity) {
    if (entity == null) return null;
    AnswerSearchResponse response = new AnswerSearchResponse();
    response.setId(entity.getId());
    response.setAnswer(HtmlImageProcessor.appendBaseUrlToImages(entity.getAnswer()));
    response.setStep(entity.getStep());
    return response;
  }
}
