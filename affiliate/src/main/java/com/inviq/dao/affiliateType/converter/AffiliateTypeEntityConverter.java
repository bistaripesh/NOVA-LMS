package com.inviq.dao.affiliateType.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import com.inviq.dto.affiliateType.response.AffiliateTypeSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateTypeEntityConverter implements Converter<AffiliateTypeEntity> {

  @Override
  public Response getResponse(AffiliateTypeEntity entity) {
    if (entity == null) return null;
    AffiliateTypeSearchResponse response = new AffiliateTypeSearchResponse();
    response.setId(entity.getId());
    response.setAffiliateType(entity.getAffiliateType());
    return response;
  }
}
