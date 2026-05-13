package com.inviq.dao.affiliate.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.affiliate.entity.LevelSubjectChapterEntity;
import com.inviq.dto.response.LevelSubjectChapterSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class LevelSubjectChapterEntityConverter implements Converter<LevelSubjectChapterEntity> {
  @Override
  public Response getResponse(LevelSubjectChapterEntity entity) {
    if (entity == null) return null;
    LevelSubjectChapterSearchResponse response = new LevelSubjectChapterSearchResponse();
    response.setId(entity.getId());
    response.setChapterName(entity.getChapter());
    response.setDescription(entity.getDescription());
    response.setLevelSubjectId(entity.getLevelSubject().getId());
    response.setLevelId(entity.getLevelSubject().getLevel().getId());
    response.setLevelName(entity.getLevelSubject().getLevel().getLevel());
    response.setSubjectId(entity.getLevelSubject().getSubject().getId());
    response.setSubjectName(entity.getLevelSubject().getSubject().getSubject());
    return response;
  }
}
