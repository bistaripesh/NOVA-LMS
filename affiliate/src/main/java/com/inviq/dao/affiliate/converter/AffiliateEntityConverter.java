package com.inviq.dao.affiliate.converter;

import com.inviq.core.constants.NovaLmsEnvConstants;
import com.inviq.core.dto.response.Response;
import com.inviq.core.entity.Converter;
import com.inviq.core.storage.BucketType;
import com.inviq.core.storage.ObjectStorageUtil;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import com.inviq.dto.affiliate.response.AffiliateSearchResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateEntityConverter implements Converter<AffiliateEntity> {
  @Override
  public Response getResponse(AffiliateEntity entity) {
    if (entity == null) return null;
    AffiliateSearchResponse response = new AffiliateSearchResponse();
    response.setId(entity.getId());
    if (entity.getDisplayPicture() != null)
      response.setProfilePicture(
          ObjectStorageUtil.getPreSignedUrl(
              BucketType.CONTABO,
              NovaLmsEnvConstants.CONTABO_S3_BUCKET_NAME,
              entity.getDisplayPicture()));
    response.setFullName(entity.getFullName());
    response.setEmail(entity.getEmail());
    response.setPhoneNumber(entity.getPhoneNumber());
    response.setLocalAddress(entity.getLocalAddress());
    response.setCity(entity.getCity());
    response.setState(entity.getState());
    response.setAffiliateType(entity.getAffiliateType().getAffiliateType());
    return response;
  }
}
