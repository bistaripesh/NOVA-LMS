package com.inviq.dao.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.entity.MaterialEntity;
import com.inviq.dto.tenset.response.MaterialSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class MaterialEntityConverter implements Converter<MaterialEntity> {

  @Override
  public Response getResponse(MaterialEntity entity) {
    if (entity == null) return null;
    MaterialSearchResponse response = new MaterialSearchResponse();
    response.setId(entity.getId());
    response.setMaterial(entity.getMaterial());
    return response;
  }
}
