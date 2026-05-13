package com.inviq.dao.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.entity.SubjectEntity;
import com.inviq.dto.tenset.response.SubjectSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class SubjectEntityConverter implements Converter<SubjectEntity> {

  @Override
  public Response getResponse(SubjectEntity entity) {
    if (entity == null) return null;
    SubjectSearchResponse response = new SubjectSearchResponse();
    response.setId(entity.getId());
    response.setSubjectName(entity.getSubject());
    return response;
  }
}
