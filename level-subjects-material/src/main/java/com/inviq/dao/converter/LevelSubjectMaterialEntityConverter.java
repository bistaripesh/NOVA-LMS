package com.inviq.dao.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.entity.LevelSubjectMaterialEntity;
import com.inviq.dto.tenset.response.LevelSubjectMaterialSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class LevelSubjectMaterialEntityConverter implements Converter<LevelSubjectMaterialEntity> {
  @Override
  public Response getResponse(LevelSubjectMaterialEntity entity) {
    if (entity == null) return null;
    LevelSubjectMaterialSearchResponse response = new LevelSubjectMaterialSearchResponse();
    response.setId(entity.getId());
    response.setMaterialId(entity.getMaterial().getId());
    response.setMaterialName(entity.getMaterial().getMaterial());
    response.setLevelSubjectId(entity.getLevelSubject().getId());
    response.setSubjectName(entity.getLevelSubject().getSubject().getSubject());
    return response;
  }
}
