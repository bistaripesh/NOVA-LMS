package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constants.AffiliateConstant;
import com.inviq.core.constants.NovaLmsEnvConstants;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.ObjectStorageExceptionManager;
import com.inviq.core.service.usecase.AbstractUpdateUseCase;
import com.inviq.core.storage.BucketType;
import com.inviq.core.storage.ObjectStorageUtil;
import com.inviq.core.util.FileUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.SystemUtil;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import com.inviq.dto.affiliate.request.AffiliateUpdateRequest;
import com.inviq.dto.affiliate.response.AffiliateUpdateResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateUpdateUseCase
    extends AbstractUpdateUseCase<
        AffiliateEntity, AffiliateUpdateRequest, AffiliateUpdateResponse, AffiliateFilter> {

  public AffiliateUpdateUseCase(
      BaseDao<AffiliateEntity, AffiliateFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preValidateRequest(AffiliateUpdateRequest request) {}

  @Override
  protected void validateRequestWithEntity(
      AffiliateUpdateRequest request, AffiliateEntity entityInRecord) {}

  @Override
  protected void preProcessRequest(AffiliateUpdateRequest request) {
    if (request.getProfilePictureMultipart() == null
        || request.getProfilePictureMultipart().isEmpty()) return;

    try {
      request.setProfileImageLocation(
          ObjectStorageUtil.upload(
              BucketType.CONTABO,
              SystemUtil.getEnv(NovaLmsEnvConstants.CONTABO_S3_BUCKET_NAME),
              request.getProfilePictureMultipart().getBytes(),
              AffiliateConstant.AFFILIATE_FILE_LOCATION + StringUtil.getUUID(),
              FileUtil.getFileExtension(request.getProfilePictureMultipart())));
    } catch (Exception e) {
      throw new AppException(
          ObjectStorageExceptionManager.ObjectStorageError.FAILED_TO_UPLOAD_FILE);
    }
  }

  @Override
  protected Module getModule() {
    return Module.AFFILIATE;
  }

  @Override
  protected AffiliateFilter prepareFilterToFindDuplicateRecord(AffiliateUpdateRequest request) {
    AffiliateFilter affiliateFilter = new AffiliateFilter();
    affiliateFilter.setEmail(request.getEmail());
    affiliateFilter.setPhoneNumber(request.getPhoneNumber());
    return affiliateFilter;
  }

  @Override
  protected AffiliateEntity prepareEntityForUpdate(
      AffiliateEntity entity, AffiliateUpdateRequest request) {
    if (request.getIsProfilePictureRemoveRequested()) {
      entity.setDisplayPicture("");
    }
    entity.setFullName(request.getFullName());
    entity.setEmail(request.getEmail());
    entity.setPhoneNumber(request.getPhoneNumber());
    entity.setLocalAddress(request.getLocalAddress());
    entity.setCity(request.getCity());
    entity.setState(request.getState());
    entity.setAffiliateType(
        new AffiliateTypeEntity() {
          {
            setId(request.getAffiliateTypeId());
          }
        });
    return entity;
  }

  @Override
  protected AffiliateUpdateResponse prepareResponse(AffiliateEntity entityToUpdate) {
    return new AffiliateUpdateResponse() {
      {
        setId(entityToUpdate.getId());
      }
    };
  }
}
