package com.inviq.serviceImpl.affiliate.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inviq.audit.dao.AuditDao;
import com.inviq.constants.AffiliateConstant;
import com.inviq.core.constants.NovaLmsEnvConstants;
import com.inviq.core.entity.BaseDao;
import com.inviq.core.entity.Module;
import com.inviq.core.exception.AppException;
import com.inviq.core.exception.manager.ObjectStorageExceptionManager;
import com.inviq.core.service.usecase.AbstractAddUseCase;
import com.inviq.core.storage.BucketType;
import com.inviq.core.storage.ObjectStorageUtil;
import com.inviq.core.util.FileUtil;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.SystemUtil;
import com.inviq.dao.affiliate.entity.AffiliateEntity;
import com.inviq.dao.affiliate.filter.AffiliateFilter;
import com.inviq.dao.affiliateType.entity.AffiliateTypeEntity;
import com.inviq.dto.affiliate.request.AffiliateAddRequest;
import com.inviq.dto.affiliate.response.AffiliateAddResponse;

/**
 * @author ripesh created on 12/05/2026
 */
public class AffiliateAddUseCase
    extends AbstractAddUseCase<
        AffiliateEntity, AffiliateAddRequest, AffiliateAddResponse, AffiliateFilter> {

  public AffiliateAddUseCase(
      BaseDao<AffiliateEntity, AffiliateFilter> entityDao,
      AuditDao auditDao,
      ObjectMapper objectMapper) {
    super(entityDao, auditDao, objectMapper);
  }

  @Override
  protected void preProcessRequest(AffiliateAddRequest request) {
    try {
      if (request.getProfilePictureMultipart() == null
          || request.getProfilePictureMultipart().isEmpty()) return;
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
  protected void preValidateRequest(AffiliateAddRequest request) {}

  @Override
  protected Module getModule() {
    return Module.AFFILIATE;
  }

  @Override
  protected AffiliateFilter prepareFilterToFindDuplicateRecord(AffiliateAddRequest request) {
    AffiliateFilter affiliateFilter = new AffiliateFilter();
    affiliateFilter.setEmail(request.getEmail());
    affiliateFilter.setPhoneNumber(request.getPhoneNumber());
    return affiliateFilter;
  }

  @Override
  protected AffiliateEntity prepareEntity(AffiliateAddRequest request) {
    AffiliateEntity affiliateEntity = new AffiliateEntity();
    affiliateEntity.setDisplayPicture(request.getProfileImageLocation());
    affiliateEntity.setFullName(request.getFullName());
    affiliateEntity.setEmail(request.getEmail());
    affiliateEntity.setPhoneNumber(request.getPhoneNumber());
    affiliateEntity.setAffiliateType(
        new AffiliateTypeEntity() {
          {
            setId(request.getAffiliateTypeId());
          }
        });
    affiliateEntity.setLocalAddress(request.getLocalAddress());
    affiliateEntity.setCity(request.getCity());
    affiliateEntity.setState(request.getState());
    return affiliateEntity;
  }

  @Override
  protected AffiliateAddResponse prepareResponse(AffiliateEntity addedEntity) {
    return new AffiliateAddResponse() {
      {
        setId(addedEntity.getId());
      }
    };
  }
}
