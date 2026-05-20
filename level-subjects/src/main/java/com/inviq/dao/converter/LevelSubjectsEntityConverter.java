package com.inviq.dao.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.entity.LevelSubjectsEntity;
import com.inviq.dto.response.LevelSubjectsSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class LevelSubjectsEntityConverter implements Converter<LevelSubjectsEntity> {
  @Override
  public Response getResponse(LevelSubjectsEntity entity) {
    if (entity == null) return null;

    LevelSubjectsSearchResponse response = new LevelSubjectsSearchResponse();
    response.setId(entity.getId());
    response.setSubjectId(entity.getSubject().getId());
    response.setSubjectName(entity.getSubject().getSubject());
    response.setLevelId(entity.getLevel().getId());
    response.setLevelName(entity.getLevel().getLevel());
    response.setPrice(entity.getPrice());
    return response;
  }
}
