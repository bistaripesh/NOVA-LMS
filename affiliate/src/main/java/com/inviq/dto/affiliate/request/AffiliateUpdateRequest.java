package com.inviq.dto.affiliate.request;

import com.inviq.core.dto.request.crud.update.BaseUpdateRequest;
import com.inviq.core.exception.AppException;
import com.inviq.core.util.StringUtil;
import com.inviq.core.util.validator.email.EmailValidator;
import com.inviq.core.util.validator.phone.PhoneNumValidator;
import com.inviq.exception.AffiliateExceptionManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ripesh created on 12/05/2026
 */
@Getter
@Setter
public class AffiliateUpdateRequest extends BaseUpdateRequest {
  private MultipartFile profilePictureMultipart;
  private String profileImageLocation;
  private String affiliateTypeId;
  private String fullName;
  private String email;
  private String phoneNumber;
  private String localAddress;
  private String city;
  private String state;
  private Boolean isProfilePictureRemoveRequested;

  @Override
  public void validateRequest() {
    if (StringUtil.isBlankOrNull(super.getId()))
      throw new AppException(AffiliateExceptionManager.AffiliateError.AFFILIATE_ID_EMPTY);
    if (StringUtil.isBlankOrNull(this.fullName))
      throw new AppException(AffiliateExceptionManager.AffiliateError.FULL_NAME_EMPTY);
    if (StringUtil.isBlankOrNull(this.phoneNumber))
      throw new AppException(AffiliateExceptionManager.AffiliateError.PHONE_NUMBER_EMPTY);
    PhoneNumValidator.validatePhoneNumber(this.phoneNumber);
    if (StringUtil.isNotBlankOrNull(this.email)) EmailValidator.validateEmail(this.email);
    if (StringUtil.isBlankOrNull(this.affiliateTypeId))
      throw new AppException(AffiliateExceptionManager.AffiliateError.AFFILIATE_TYPE_ID_EMPTY);
  }
}
