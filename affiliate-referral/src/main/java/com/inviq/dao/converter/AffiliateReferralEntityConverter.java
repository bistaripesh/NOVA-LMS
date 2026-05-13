package com.inviq.dao.converter;

import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.dao.entity.AffiliateReferralEntity;
import com.inviq.dto.response.AffiliateReferralSearchResponse;

/**
 * @author ripesh created on 13/05/2026
 */
public class AffiliateReferralEntityConverter implements Converter<AffiliateReferralEntity> {
  @Override
  public Response getResponse(AffiliateReferralEntity entity) {
    if (entity == null) return null;
    AffiliateReferralSearchResponse response = new AffiliateReferralSearchResponse();
    response.setId(entity.getId());
    response.setCommissionPercent(entity.getCommissionPercent());
    response.setReferralCode(entity.getReferralCode());
    response.setDescription(entity.getDescription());
    response.setFullName(entity.getAffiliate().getFullName());
    response.setIsReferralCodeActive(entity.getIsReferralCodeActive());
    return response;
  }
}
