package com.inviq.dao.tenset.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.tenset.entity.TenSetEntity;
import com.inviq.dto.tenset.response.TenSetDetailSearchResponse;

/**
 * @author ripesh created on 14/05/2026
 */
public class TenSetEntityConverter implements Converter<TenSetEntity> {
  @Override
  public Response getResponse(TenSetEntity entity) {
    TenSetDetailSearchResponse response = new TenSetDetailSearchResponse();
    response.setId(entity.getId());
    response.setTitle(entity.getTitle());
    response.setYear(entity.getYear());
    response.setLevel(entity.getLevelSubjectMaterial().getLevelSubject().getLevel().getLevel());
    response.setSubject(
        entity.getLevelSubjectMaterial().getLevelSubject().getSubject().getSubject());
    return response;
  }
}
