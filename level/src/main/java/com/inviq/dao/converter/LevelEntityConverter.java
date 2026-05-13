package com.inviq.dao.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.entity.LevelEntity;
import com.inviq.dto.tenset.response.LevelSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class LevelEntityConverter implements Converter<LevelEntity> {
  @Override
  public Response getResponse(LevelEntity entity) {
    if (entity == null) return null;
    LevelSearchResponse levelSearchResponse = new LevelSearchResponse();
    levelSearchResponse.setId(entity.getId());
    levelSearchResponse.setLevelName(entity.getLevel());
    return levelSearchResponse;
  }
}
