package com.inviq.dao.qna.question.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.core.util.HtmlImageProcessor;
import com.inviq.dao.qna.question.entity.QuestionEntity;
import com.inviq.dto.qna.question.response.QuestionSearchResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ripesh created on 17/05/2026
 */
@Getter
@Setter
public class QuestionEntityConverter implements Converter<QuestionEntity> {

  @Override
  public Response getResponse(QuestionEntity entity) {
    if (entity == null) return null;
    QuestionSearchResponse response = new QuestionSearchResponse();
    response.setId(entity.getId());
    response.setQuestion(HtmlImageProcessor.appendBaseUrlToImages(entity.getQuestion()));

    response.setYear(entity.getYear());
    response.setIsDraft(entity.getIsDraft());
    response.setSubject(
        entity.getLevelSubjectMaterial().getLevelSubject().getSubject().getSubject());
    response.setLevel(entity.getLevelSubjectMaterial().getLevelSubject().getLevel().getLevel());
    response.setLevelSubjectMaterialId(entity.getLevelSubjectMaterial().getId());
    return response;
  }
}
